package resources.backend.model;

import jakarta.validation.constraints.Size;

public class CommentModel {

    private Long id;

    @Size(max = 255)
    private String content;

    private Long postId;

    private Long userId;
    
    private String username;


    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setContent(final String text) {
        this.content = text;
    }

    public void setPostId(final Long postId) {
        this.postId = postId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
