package resources.backend.model;

import jakarta.validation.constraints.Size;
public class FavoriteModel {

    private Long id;
    @Size(max = 255)
    private Long postId;
    @Size(max = 255)
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
