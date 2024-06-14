package resources.backend.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import resources.backend.service.PostService;
import resources.backend.model.PostModel;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostsRestController {

    private final PostService postService;

    public PostsRestController(final PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostModel>> getPosts(@RequestParam(required = false) Long categoryId) {
        List<PostModel> posts;
        if (categoryId != null) {
            posts = postService.getPostsByCategory(categoryId);
        } else {
            posts = postService.findAll();
        }
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostModel> getPost(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(postService.get(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<PostModel> createPost(@Valid @RequestBody PostModel postDTO) {
        Long postId = postService.createPost(postDTO);
        // Retrieve the newly created post and return it with status code 201
        PostModel createdPost = postService.get(postId); // Assuming there's a method to retrieve a post by ID
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostModel> updatePost(@PathVariable(name = "id") final Long id,
            @Valid @RequestBody PostModel postDTO) {
        postService.update(id, postDTO);
        // Retrieve the updated post and return it
        return ResponseEntity.ok(postService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable(name = "id") final Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
