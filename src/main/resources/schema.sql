create table if not exists users_lab1
(
    id         integer not null primary key,
    email      varchar(255),
    name       varchar(30),
    password   varchar(255),
    profile_id integer,
    username   varchar(255)
);

create table if not exists users_lab2
(
    id         integer not null primary key,
    email      varchar(255),
    name       varchar(30),
    password   varchar(255),
    profile_id varchar(255),
    username   varchar(255)
    );

create table if not exists users_lab3
(
    id         integer not null primary key,
    email      varchar(255),
    name       varchar(30),
    password   varchar(255),
    profile_id varchar(255),
    username   varchar(255)
    );

