package resources.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import resources.backend.entity.PostLike;
import resources.backend.entity.PostLikeId;

import java.util.List;

public interface PostLikeRepos extends JpaRepository<PostLike, PostLikeId> {
    List<PostLike> findByIdUserId(Long userId);
    List<PostLike> findByIdPostId(Integer postId);
}