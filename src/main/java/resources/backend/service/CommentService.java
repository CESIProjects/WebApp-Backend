package resources.backend.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import resources.backend.entity.Comment;
import resources.backend.model.CommentModel;
import resources.backend.repos.CommentRepos;
import resources.backend.util.NotFoundException;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepos commentRepository;

    public CommentService(final CommentRepos commentRepository) {
        this.commentRepository = commentRepository;
    }


    public List<CommentModel> findAll() {
        final List<Comment> comments = commentRepository.findAll(Sort.by("id"));

        return comments.stream()
            .map(comment -> mapToDTO(comment, new CommentModel()))
            .toList();
    }


    public CommentModel get(final Long id) {
        return commentRepository.findById(id)
            .map(comment -> mapToDTO(comment, new CommentModel()))
            .orElseThrow(NotFoundException::new);
    }

    public Long create(final CommentModel commentDTO) {
        final Comment comment = new Comment();
        mapToEntity(commentDTO, comment);

        return commentRepository.save(comment).getId();
    }

    public void update(final Long id, final CommentModel commentDTO) {
        final Comment comment = commentRepository.findById(id)
            .orElseThrow(NotFoundException::new);
        mapToEntity(commentDTO, comment);
        commentRepository.save(comment);
    }

    public void delete(final Long id) {
        commentRepository.deleteById(id);
    }

    private CommentModel mapToDTO(final Comment comment, final CommentModel commentDTO) {
        commentDTO.setId(comment.getId());
        commentDTO.setTitle(comment.getTitle());
        commentDTO.setText(comment.getText());
        commentDTO.setPostId(comment.getPostId());
        commentDTO.setUserId(comment.getUserId());
        return commentDTO;
    }

    private Comment mapToEntity(final CommentModel commentDTO, final Comment comment) {
        comment.setTitle(commentDTO.getTitle());
        comment.setText(commentDTO.getText());
        comment.setPostId(commentDTO.getPostId());
        comment.setUserId(commentDTO.getUserId());
        return comment;
    }
}
