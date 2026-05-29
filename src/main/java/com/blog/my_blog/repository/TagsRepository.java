package com.blog.my_blog.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blog.my_blog.entity.Tag;

@Repository
public interface TagsRepository extends JpaRepository<Tag, UUID> {
    Optional<Tag> findByName(String name);
}
