create table car(
id integer primary key,
mark varchar(30),
model varchar(30),
cost integer
);

create table persons(
id integer primary key,
name varchar(50),
age int,
has_driver_license boolean,
car integer references car(id)
);
