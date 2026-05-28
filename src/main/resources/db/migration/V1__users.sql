create table USERS (
    id UUID PRIMARY KEY NOT NULL,
    username VARCHAR(64)  NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT "READER",
    created_at TIMESTAMPTZ DEFAULT now()
);