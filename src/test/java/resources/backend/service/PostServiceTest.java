package resources.backend.service;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostServiceTest {
   
  @Autowired
  private PostService postService;

   @Test
  void testFindAll() {
    var result = postService.findAll();
    assertFalse(result.isEmpty());
   }
}
