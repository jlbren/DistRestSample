
create database if not exists DistRestSample;



CREATE TABLE if not exists DistRestSample.Customer (
	id bigint,
	firstname varchar(30),
	lastname varchar(30),
	birthdate varchar(30),
	PRIMARY KEY(id)
);

create table if not exists DistRestSample.Product (
	id bigint,
	name varchar(30),
	manufacturer varchar(30),
	primary key(id)
);

create table if not exists DistRestSample.Inventory (
	productid bigint,
	count int
);

create table if not exists DistRestSample.Orders (
	customerid bigint,
	productid bigint,
	int count
);

insert into DistRestSample.Product (id, name, manufacturer) values (1, 'prod1', 'factory1');

insert into DistRestSample.Product (id, name, manufacturer) values (2, 'prod2', 'factory2');



