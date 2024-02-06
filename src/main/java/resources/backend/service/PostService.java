package resources.backend.service;

import org.springframework.stereotype.Service;
import resources.backend.domain.Post;
import resources.backend.model.PostDTO;
import resources.backend.repos.PostRepository;
import resources.backend.util.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostDTO> findAll() {
        return postRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PostDTO get(final Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return mapToDTO(post);
    }

    public Long create(final PostDTO postDTO) {
        Post post = mapToEntity(postDTO);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    public void update(final Long id, final PostDTO postDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(postDTO, post);
        postRepository.save(post);
    }

    public void delete(final Long id) {
        postRepository.deleteById(id);
    }

    private PostDTO mapToDTO(final Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        // Map other attributes as well
        return postDTO;
    }

    private Post mapToEntity(final PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        // Map other attributes as well
        return post;
    }

    private void mapToEntity(final PostDTO postDTO, final Post post) {
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        // Map other attributes as well
    }
}
