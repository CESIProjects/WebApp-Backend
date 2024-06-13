package resources.backend.model;

public class PostLikeModel {



    private Long postId;

    private Long userId;


    public Long getPostId() {
        return postId;
    }

    public void setPostId(final Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }


    public void setUserId(final Long userId) {
        this.userId = userId;
    }


}
