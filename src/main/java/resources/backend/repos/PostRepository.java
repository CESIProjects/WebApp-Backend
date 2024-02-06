package resources.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import resources.backend.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}