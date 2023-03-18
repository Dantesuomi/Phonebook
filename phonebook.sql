CREATE DATABASE IF NOT EXISTS phonebook;

USE phonebook;

CREATE TABLE Phonebook(
	id int auto_increment primary key, -- autofill id column
    fullName      varchar(100),
    phoneNumber   varchar(100),
    email         varchar(100)
);