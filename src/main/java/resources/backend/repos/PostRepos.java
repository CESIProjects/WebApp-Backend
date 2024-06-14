package resources.backend.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import resources.backend.entity.Post;

public interface PostRepos extends JpaRepository<Post, Long> {
  List<Post> findByCategoryId(Long categoryId);
}