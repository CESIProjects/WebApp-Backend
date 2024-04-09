package resources.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import resources.backend.entity.Post;

public interface PostRepos extends JpaRepository<Post, Long> {
}