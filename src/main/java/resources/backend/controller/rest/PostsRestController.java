package resources.backend.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import resources.backend.model.PostModel;
import resources.backend.service.PostService;

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
    public ResponseEntity<List<PostModel>> getAllPosts() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostModel> getPost(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(postService.get(id));
    }

    @PostMapping("/posts")
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
