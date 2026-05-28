package com.blog.my_blog.entity;

import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String role;
    private Date created_at;
        
    protected User(){};

    public User(UUID id ,String username ,String email,String password,String role,Date created_at ){
        this.id = id;
        this.username = username;
        this.email= email;
        this.password = password;
        this.role = role;
        this.created_at = created_at;
    }

    public UUID getId(){
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getRole(){
        return role;
    }
    public Date getCreatedAt(){
        return created_at;
    }
}
