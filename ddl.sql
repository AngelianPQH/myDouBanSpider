drop database spider;
create database spider;
use spider;

create table film(
	id int primary key auto_intrement,
	title varchar(40),
	poster varchar(40),
	star double,
	rating varchar(140),
	quote varchar(140),
	director varchar(40),
    actor varchar(50),
    time varchar(40),
    country varchar(40),
    type varchar(100),
	
);
