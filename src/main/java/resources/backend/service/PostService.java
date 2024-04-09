package resources.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import resources.backend.entity.Post;
import resources.backend.model.PostModel;
import resources.backend.repos.PostRepos;
import resources.backend.util.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private  final PostRepos postRepository;

    public PostService(final PostRepos postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostModel> findAll() {
        return postRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PostModel get(final Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return mapToDTO(post);
    }




    public Long createPost(PostModel postDTO) {
        Post post = mapToEntity(postDTO);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }


    public void update(final Long id, final PostModel postDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(postDTO, post);
        postRepository.save(post);
    }

    public void delete(final Long id) {
        postRepository.deleteById(id);
    }

    private PostModel mapToDTO(final Post post) {
        PostModel postDTO = new PostModel();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setPublicationDate(post.getPublicationDate());
        postDTO.setPopular(post.getPopular());
        postDTO.setUserId(post.getUserId());
        postDTO.setCategoryId(post.getCategoryId());
        return postDTO;
    }

    private Post mapToEntity(final PostModel postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setPublicationDate(new java.sql.Date(postDTO.getPublicationDate().getTime()));
        post.setPopular(postDTO.getPopular());
        post.setUserId(postDTO.getUserId());
        post.setCategoryId(postDTO.getCategoryId());
        return post;
    }

    private void mapToEntity(final PostModel postDTO, final Post post) {
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        // Map other attributes as well
    }
}
