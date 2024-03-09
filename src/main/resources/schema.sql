create table if not exists users
(
    id         integer not null primary key,
    email      varchar(255),
    name       varchar(30),
    password   varchar(255),
    profile_id integer,
    username   varchar(255)
);
