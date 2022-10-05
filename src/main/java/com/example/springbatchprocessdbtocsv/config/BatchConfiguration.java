package com.example.springbatchprocessdbtocsv.config;

import com.example.springbatchprocessdbtocsv.listener.UserJobExecutionNotificationListener;
import com.example.springbatchprocessdbtocsv.listener.UserStepCompleteNotificationListener;
import com.example.springbatchprocessdbtocsv.model.User;
import com.example.springbatchprocessdbtocsv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

@Configuration // Informs Spring that this class contains configurations
@EnableBatchProcessing // Enables batch processing for the application
@RequiredArgsConstructor
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final UserRepository userRepository;

    Date now = new Date(); // java.util.Date, NOT java.sql.Date or java.sql.Timestamp!
    String format1 = new SimpleDateFormat("yyyy-MM-dd'-'HH-mm-ss-SSS",Locale.forLanguageTag("tr-TR")).format(now);
    private Resource outputResource = new FileSystemResource("output/customers_" + format1 + ".csv");

    @Bean
    public RepositoryItemReader<User> reader(){
        RepositoryItemReader<User> repositoryItemReader = new RepositoryItemReader<>();
        repositoryItemReader.setRepository(userRepository);
        repositoryItemReader.setMethodName("findAll");
        final HashMap<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);
        repositoryItemReader.setSort(sorts);
        return repositoryItemReader;
    }

    @Bean
    public FlatFileItemWriter<User> writer() {

        FlatFileItemWriter<User> writer = new FlatFileItemWriter<>();

        writer.setResource(outputResource);
        writer.setAppendAllowed(true);

        writer.setLineAggregator(new DelimitedLineAggregator<User>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<User>() {
                    {
                        setNames(new String[]{"ID", "AGE", "BIRTHDAY", "COUNTRY", "EMAIL", "FIRST_NAME", "GENDER", "LAST_NAME", "PERSON_ID"});
                    }
                });
            }
        });

        return writer;
    }

    @Bean
    public UserProcessor processor() {
        return new UserProcessor();
    }

    @Bean
    public UserJobExecutionNotificationListener stepExecutionListener() {
        return new UserJobExecutionNotificationListener(userRepository);
    }


    @Bean
    public UserStepCompleteNotificationListener jobExecutionListener() {
        return new UserStepCompleteNotificationListener();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("csv-step").<User, User>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .listener(stepExecutionListener())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job runJob() {
        return jobBuilderFactory.get("importuserjob")
                .listener(jobExecutionListener())
                .flow(step1()).end().build();

    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }
}