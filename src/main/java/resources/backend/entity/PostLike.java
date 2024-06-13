package resources.backend.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_like")
public class PostLike {

    @EmbeddedId
    private PostLikeId id;

    // Default constructor
    public PostLike() {}

    // Parameterized constructor
    public PostLike(PostLikeId id) {
        this.id = id;
    }

    // Getters and setters
    public PostLikeId getId() {
        return id;
    }

    public void setId(PostLikeId id) {
        this.id = id;
    }
}
