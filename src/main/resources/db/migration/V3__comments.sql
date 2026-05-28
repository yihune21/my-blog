create table COMMENTS (
    id UUID PRIMARY KEY NOT NULL,
    content VARCHAR NOT NULL,
    post_id UUID NOT NULL REFERENCES posts(id) ON DELETE CASCADE, 
    author_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE, 
    created_at TIMESTAMPTZ DEFAULT now()
);
