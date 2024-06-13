package resources.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import resources.backend.entity.Comment;

public interface CommentRepos extends JpaRepository<Comment, Long> {
}
