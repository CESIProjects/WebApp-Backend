package resources.backend.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import resources.backend.model.PostDTO;
import resources.backend.service.PostService;

import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostsResource {

    private final PostService postService;

    public PostsResource(final PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(postService.get(id));
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        Long postId = postService.create(postDTO);
        // Retrieve the newly created post and return it with status code 201
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.get(postId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable(name = "id") final Long id,
                                              @Valid @RequestBody PostDTO postDTO) {
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
