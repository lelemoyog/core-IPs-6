SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS users (
userId int PRIMARY KEY auto_increment,
userName VARCHAR,
companyPosition VARCHAR,
departmentId INTEGER
);

CREATE TABLE IF NOT EXISTS news (
newsId int PRIMARY KEY auto_increment,
generalNews VARCHAR,
departmentId INTEGER,
userId INTEGER
);

CREATE TABLE IF NOT EXISTS departments (
departmentId int PRIMARY KEY auto_increment,
departmentName VARCHAR,
description VARCHAR
);

CREATE TABLE IF NOT EXISTS departments_users(
id int PRIMARY KEY auto_increment,
userId INTEGER,
departmentId INTEGER
);



