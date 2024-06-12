package resources.backend.model;

import jakarta.validation.constraints.Size;

public class CommentModel {

    private Long id;

    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String text;

    private Long postId;

    private Long userId;


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
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

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public void setPostId(final Long postId) {
        this.postId = postId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }



}
