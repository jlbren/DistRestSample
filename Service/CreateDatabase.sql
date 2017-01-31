
create database if not exists DistRestSample;



CREATE TABLE if not exists DistRestSample.Customer (
	id bigint,
	firstname varchar(30),
	lastname varchar(30),
	birthdate varchar(30),
	PRIMARY KEY(id)
);


