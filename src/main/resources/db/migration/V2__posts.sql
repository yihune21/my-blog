create table POSTS (
    id UUID PRIMARY KEY NOT NULL,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR, 
    content VARCHAR NOT NULL,
    summary VARCHAR, 
    status VARCHAR(20) NOT NULL, 
    author_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE, 
    created_at TIMESTAMPTZ DEFAULT now(), 
    updated_at TIMESTAMPTZ
);
