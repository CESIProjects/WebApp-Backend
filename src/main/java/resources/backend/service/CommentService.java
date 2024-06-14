package resources.backend.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import resources.backend.entity.Comment;
import resources.backend.model.CommentModel;
import resources.backend.repos.CommentRepos;
import resources.backend.repos.UserRepos;
import resources.backend.util.NotFoundException;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepos commentRepository;
    private final UserRepos userRepository;

    public CommentService(final CommentRepos commentRepository, final UserRepos userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }


    public List<CommentModel> findAll() {
        final List<Comment> comments = commentRepository.findAll(Sort.by("id"));

        return comments.stream()
            .map(comment -> mapToDTO(comment, new CommentModel()))
            .toList();
    }

    public List<CommentModel> findByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
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
        commentDTO.setContent(comment.getContent());
        commentDTO.setPostId(comment.getPostId());
        commentDTO.setUserId(comment.getUserId());
        String username = userRepository.findById(comment.getUserId())
                .orElseThrow(NotFoundException::new)
                .getUsername();
        commentDTO.setUsername(username);
        return commentDTO;
    }

    private Comment mapToEntity(final CommentModel commentDTO, final Comment comment) {
        comment.setContent(commentDTO.getContent());
        comment.setPostId(commentDTO.getPostId());
        comment.setUserId(commentDTO.getUserId());
        return comment;
    }
}
