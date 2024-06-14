package resources.backend.controller.rest;
import jakarta.validation.Valid;
import resources.backend.model.CommentModel;
import resources.backend.service.CommentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentRestController {

    private final CommentService commentService;

    @Autowired
    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentModel>> getAllComment() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentModel> getComment(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(commentService.get(id));
    }

    @PostMapping("/post")
    public ResponseEntity<CommentModel> createComment(@Valid @RequestBody CommentModel commentDTO) {
        Long commentId = commentService.create(commentDTO);
        // Retrieve the newly created comment and return it with status code 201
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.get(commentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentModel> updateComment(@PathVariable(name = "id") final Long id,
        @Valid @RequestBody CommentModel commentDTO) {
        commentService.update(id, commentDTO);
        // Retrieve the updated comment and return it
        return ResponseEntity.ok(commentService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "id") final Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentModel>> getCommentsByPostId(@PathVariable(name = "postId") final Long postId) {
        return ResponseEntity.ok(commentService.findByPostId(postId));
    }
}
