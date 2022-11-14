use projdata;

create table flight (

	-- Generate a unique 8 character order id for the order
	id int auto_increment,

	-- Your email, must be the same as LumiNUS
	email varchar(256) not null,

	adult int not null,
	origin varchar(256) not null,
	destination varchar(256) not null,
	date varchar(256) not null,

	primary key(id),
    FOREIGN KEY (email) REFERENCES login(email)
);
