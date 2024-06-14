package resources.backend.entity;

import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    public String content;
    @Column
    public Long postId;
    @Column
    public Long userId;


    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    public void setContent(final String text) {
        this.content = text;
    }

    public void setPostId(final Long postId) {
        this.postId = postId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }




}
