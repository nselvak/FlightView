create database projdata;

use projdata;

create table login (

	-- Generate a unique 8 character order id for the order
	-- id int auto_increment,


	-- Your email, must be the same as LumiNUS
	email varchar(256) not null,


    -- password
	password varchar(256) not null,

	primary key(email)
);
