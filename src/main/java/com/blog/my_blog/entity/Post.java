package com.blog.my_blog.entity;

import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;
    private  String title;
    private String slug;
    private String content;
    private String summary;
    private String status;
    private UUID author_id;
    private Date created_at;
    private Date  update_at;
    
    protected Post(){};
    public Post(UUID id ,String title ,String slug,String content,String summary,String status,String author_id,Date created_at,Date update_at){
        this.id = id;
        this.title = title;
        this.slug= slug;
        this.content = content;
        this.summary = summary;
        this.status = status;
        this.created_at = created_at;
        this.update_at = update_at;
    }

    public UUID getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getSlug(){
        return slug;
    }
    public String getContent(){
        return content;
    }
    public String getSummary(){
        return summary;
    }
    public String getStatus(){
        return status;
    }
    public UUID getAuthorId(){
        return author_id;
    }
    public Date getCreatedAt(){
        return created_at;
    }
    public Date getUpdateAt(){
        return update_at;
    }
}
