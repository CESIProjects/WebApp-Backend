package resources.backend.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import resources.backend.entity.PostLike;
import resources.backend.service.PostLikeService;


import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/likes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostLikeController {

    private final PostLikeService postLikeService;

    @Autowired
    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @PostMapping("/post")
    public ResponseEntity<Void> createLike(@RequestParam Long userId, @RequestParam Integer postId) {
        postLikeService.create(userId, postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteLike(@RequestParam Long userId, @RequestParam Integer postId) {
        postLikeService.delete(userId, postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostLike>> getLikesByUserId(@PathVariable Long userId) {
        List<PostLike> likes = postLikeService.getLikesByUserId(userId);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<PostLike>> getLikesByPostId(@PathVariable Integer postId) {
        List<PostLike> likes = postLikeService.getLikesByPostId(postId);
        return ResponseEntity.ok(likes);
    }
}
