package resources.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import resources.backend.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepos extends JpaRepository<Post, Long> {


    Optional<List<Post>> findByUserId(Long userId);
}