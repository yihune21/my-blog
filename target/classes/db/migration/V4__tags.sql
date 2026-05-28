create table TAGS (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(64)  NOT NULL UNIQUE 
);
