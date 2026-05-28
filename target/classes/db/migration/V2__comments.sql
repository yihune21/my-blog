create table COMMENTS (
    ID int not null,
    content varchar not null,
    post_id (FK), 
    author_id (FK), 
    created_at timestamp
);
