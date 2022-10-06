DROP TABLE IF EXISTS USERS ;

CREATE TABLE USERS (
    "ID" BIGINT auto_increment NOT NULL PRIMARY KEY,
    "AGE" INTEGER NOT NULL,
    "BIRTHDAY" DATE,
    "COUNTRY" CHARACTER VARYING(255),
    "EMAIL" CHARACTER VARYING(255),
    "FIRST_NAME" CHARACTER VARYING(255),
    "GENDER" CHARACTER VARYING(255),
    "LAST_NAME" CHARACTER VARYING(255),
    "PERSON_ID" CHARACTER VARYING(255)
);


INSERT INTO USERS VALUES (1, 22, DATE '2000-08-15', 'China', 'gspearing0@flickr.com', 'Ginnie', 'FEMALE', 'Spearing', 'fa26fa96-97d3-4e8e-856a-fdf07499e13e');
INSERT INTO USERS VALUES (2, 23, DATE '1999-01-25', 'Panama', 'nloynes2@woothemes.com', 'Natala', 'FEMALE', 'Loynes', '24be24e6-525f-42de-855d-52d4fef21608');
INSERT INTO USERS VALUES (3, 9, DATE '2013-04-15', 'Japan', 'jheino3@mayoclinic.com', 'Jard', 'MALE', 'Heino', '87cdda81-45d0-451a-a62f-f8450eae1b64');
INSERT INTO USERS VALUES (4, 8, DATE '2014-05-09', 'Indonesia', 'jcarlaw1@t.co', 'Jilleen', 'FEMALE', 'Carlaw', 'c722e6d5-9024-49c5-80e0-c2555f1eb9cc');
INSERT INTO USERS VALUES (5, 22, DATE '2000-03-18', 'Indonesia', 'rgillino6@china.com.cn', 'Rainer', 'MALE', 'Gillino', '5302a199-f313-4a24-9550-d643001d9faf');
INSERT INTO USERS VALUES (6, 9, DATE '2013-02-16', 'China', 'rcossans4@harvard.edu', 'Roseline', 'FEMALE', 'Cossans', '06f70b0d-2c98-4f46-b933-528499ab91b3');