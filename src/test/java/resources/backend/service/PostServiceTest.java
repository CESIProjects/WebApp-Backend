package resources.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import resources.backend.entity.Post;
import resources.backend.model.PostModel;
import resources.backend.repos.PostRepos;
import resources.backend.util.NotFoundException;

class PostServiceTest {

    @Mock
    private PostRepos postRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Given
        List<Post> posts = new ArrayList<>();
        posts.add(new Post());
        posts.add(new Post());
        doReturn(posts).when(postRepository).findAll();

        // When
        List<PostModel> result = postService.findAll();

        // Then
        assertEquals(2, result.size());
    }

    @Test
    void testGetExistingPost() {
        // Given
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        doReturn(Optional.of(post)).when(postRepository).findById(postId);

        // When
        PostModel result = postService.get(postId);

        // Then
        assertEquals(postId, result.getId());
    }

    @Test
    void testGetNonExistingPost() {
        // Given
        Long postId = 1L;
        doReturn(Optional.empty()).when(postRepository).findById(postId);

        // When & Then
        assertThrows(NotFoundException.class, () -> postService.get(postId));
    }

    @Test
    void testCreatePost() throws ParseException {
        // Given
        PostModel postModel = new PostModel();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date fixedPublicationDate = dateFormat.parse("2023/02/23");
        
        postModel.setTitle("Test Title");
        postModel.setContent("Test Content");
        postModel.setPublicationDate(fixedPublicationDate);

        Post post = new Post();
        post.setId(1L);
        doReturn(post).when(postRepository).save(any());

        // When
        Long postId = postService.createPost(postModel);

        // Then
        assertEquals(post.getId(), postId);
    }

    @Test
    void testUpdatePost() {
        // Given
        Long postId = 1L;
        PostModel postModel = new PostModel();
        postModel.setTitle("Updated Title");
        postModel.setContent("Updated Content");
        Post post = new Post();
        post.setId(postId);
        doReturn(Optional.of(post)).when(postRepository).findById(postId);

        // When
        postService.update(postId, postModel);

        // Then
        assertEquals(postModel.getTitle(), post.getTitle());
        assertEquals(postModel.getContent(), post.getContent());
    }

    @Test
    void testDeletePost() {
        // Given
        Long postId = 1L;
        doNothing().when(postRepository).deleteById(postId);

        // When
        postService.delete(postId);

        // Then
        verify(postRepository).deleteById(postId);
    }
}