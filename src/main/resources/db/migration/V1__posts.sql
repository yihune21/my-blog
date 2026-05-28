create table POSTS (
    ID int not null,
    TITLE varchar(100) not null,
    slug varchar, 
    content varchar not null,
    summary varchar, 
    status varchar(20) not null, 
    author_id (FK), 
    created_at timestamp, 
    updated_at timestamp
);
