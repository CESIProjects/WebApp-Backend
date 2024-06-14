package resources.backend.entity;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import java.util.Objects;

@Embeddable
public class PostLikeId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Integer postId;

    // Default constructor
    public PostLikeId() {}

    // Parameterized constructor
    public PostLikeId(Long userId, Integer postId) {
        this.userId = userId;
        this.postId = postId;
    }

    // Getters and setters

    // hashCode and equals
    @Override
    public int hashCode() {
        return Objects.hash(userId, postId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PostLikeId that = (PostLikeId) obj;
        return Objects.equals(userId, that.userId) && Objects.equals(postId, that.postId);
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

}
