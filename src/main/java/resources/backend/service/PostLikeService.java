package resources.backend.service;

import org.springframework.stereotype.Service;
import resources.backend.entity.PostLike;
import resources.backend.entity.PostLikeId;
import resources.backend.repos.PostLikeRepos;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostLikeService {

    private final PostLikeRepos postLikeRepos;

    public PostLikeService(final PostLikeRepos postLikeRepos) {
        this.postLikeRepos = postLikeRepos;
    }

    public void create(final Long userId, final Integer postId) {
        postLikeRepos.save(new PostLike(new PostLikeId(userId, postId)));
    }

    public void delete(final Long userId, final Integer postId) {
        postLikeRepos.deleteById(new PostLikeId(userId, postId));
    }

    public List<PostLike> getLikesByUserId(final Long userId) {
        return postLikeRepos.findByIdUserId(userId);
    }

    public List<Integer> findUserIdsWhoLikedPost(final Integer postId) {
        List<PostLike> likes = postLikeRepos.findByIdPostId(postId);
        List<Integer> userIds = new ArrayList<>();
        for (PostLike like : likes) {
            userIds.add(like.getUserId());
        }
        return userIds;
    }

}
