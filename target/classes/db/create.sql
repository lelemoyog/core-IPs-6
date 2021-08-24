--SET MODE PostgreSQL;

CREATE TABLE  users (
userId serial PRIMARY KEY,
userName VARCHAR,
companyPosition VARCHAR,
departmentId INTEGER
);

CREATE TABLE news (
newsId serial PRIMARY KEY,
generalNews VARCHAR,
departmentId INTEGER,
userId INTEGER
);

CREATE TABLE departments (
departmentId serial PRIMARY KEY,
departmentName VARCHAR,
description VARCHAR
);

CREATE TABLE departments_users(
id serial PRIMARY KEY,
userId INTEGER,
departmentId INTEGER
);



