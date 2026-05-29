package com.blog.my_blog;

import com.blog.my_blog.dto.request.CommentCreateRequest;
import com.blog.my_blog.dto.request.PostCreateRequest;
import com.blog.my_blog.dto.request.UserCreateRequest;
import com.blog.my_blog.dto.response.CommentResponse;
import com.blog.my_blog.dto.response.PostResponse;
import com.blog.my_blog.dto.response.UserResponse;
import com.blog.my_blog.service.CommentService;
import com.blog.my_blog.service.PostService;
import com.blog.my_blog.service.UserService;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BlogIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Test
    void contextLoads() {
        // verifies the full application context wires correctly
    }

    @Test
    void shouldCreateUserPostAndComment() {
        // 1. Create author
        UserCreateRequest userReq = new UserCreateRequest(
            "testauthor", "author@test.com", "password123", "AUTHOR"
        );
        UserResponse author = userService.createUser(userReq);
        assertThat(author.id()).isNotNull();
        assertThat(author.username()).isEqualTo("testauthor");

        // 2. Create post by that author
        PostCreateRequest postReq = new PostCreateRequest(
            "Hello World",
            "My first blog post content",
            "A short summary",
            "published",
            author.id(),
            Set.of("java", "spring")
        );
        PostResponse post = postService.createPost(postReq);
        assertThat(post.id()).isNotNull();
        assertThat(post.slug()).isEqualTo("hello-world");
        assertThat(post.tagNames()).containsExactlyInAnyOrder("java", "spring");

        // 3. Create commenter
        UserCreateRequest commenterReq = new UserCreateRequest(
            "commenter", "commenter@test.com", "password456", "READER"
        );
        UserResponse commenter = userService.createUser(commenterReq);

        // 4. Add comment
        CommentCreateRequest commentReq = new CommentCreateRequest(
            "Great post!", post.id(), commenter.id()
        );
        CommentResponse comment = commentService.createComment(commentReq);
        assertThat(comment.id()).isNotNull();
        assertThat(comment.content()).isEqualTo("Great post!");
        assertThat(comment.postId()).isEqualTo(post.id());

        // 5. Retrieve comments for post
        List<CommentResponse> comments = commentService.getCommentsByPost(post.id());
        assertThat(comments).hasSize(1);
        assertThat(comments.get(0).authorUsername()).isEqualTo("commenter");
    }

    @Test
    void shouldFetchPostsByAuthor() {
        UserResponse author = userService.createUser(
            new UserCreateRequest("blogger", "blogger@test.com", "pass", "AUTHOR")
        );

        postService.createPost(new PostCreateRequest(
            "Post One", "Content one", null, "draft", author.id(), null
        ));
        postService.createPost(new PostCreateRequest(
            "Post Two", "Content two", null, "published", author.id(), null
        ));

        List<PostResponse> posts = postService.getPostsByAuthor(author.id());
        assertThat(posts).hasSize(2);
    }
}
