drop table if exists person;
drop table if exists employee;

create table employee (
    id serial primary key not null,
    first_name text,
    last_name text,
    inn text,
    hired timestamp
);

create table person (
    id serial primary key not null,
    login varchar(2000),
    password varchar(2000),
    employee_id int references employee(id)
);

insert into employee (first_name, last_name, inn, hired) values ('Name', 'Family name', 1313, '2000-07-12 20:11');
insert into person (login, password, employee_id) values ('parsentev', '123', 1);
insert into person (login, password, employee_id) values ('ban', '123', 1);
insert into person (login, password, employee_id) values ('ivan', '123', 1);
