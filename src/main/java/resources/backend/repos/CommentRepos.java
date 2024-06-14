package resources.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import resources.backend.entity.Comment;

import java.util.List;

public interface CommentRepos extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}
