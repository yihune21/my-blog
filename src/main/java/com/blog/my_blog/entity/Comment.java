package com.blog.my_blog.entity;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;
    private String content;
    private UUID post_id;
    private UUID author_id;
    private Date created_at;

    protected Comment(){};

    public Comment(UUID id,String content,UUID post_id, UUID author_id ,Date created_at){
        this.id=id;
        this.content=content;
        this.post_id=post_id;
        this.author_id=author_id;
        this.created_at=created_at;
    }

    public UUID getId(){
        return id;
    }
    public String getContent(){
        return content;
    }
    public UUID getPostId(){
        return post_id;
    }
    public UUID getAuthorId(){
        return author_id;
    }
    public Date getCreatedAt(){
        return created_at;
    }

}
