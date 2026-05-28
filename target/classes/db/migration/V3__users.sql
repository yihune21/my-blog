create table USERS (
    ID int not null,
    username varchar not null unique,
    email varchar not null unique,
    password_hash varchar not null,
    role varchar(10),
    created_at timestamp
);