package resources.backend.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerServiceTest {
   
  @Autowired
  private CustomerService customerService;

   @Test
  void testFindAll() {
    var result = customerService.findAll();
    assertTrue(result.isEmpty());
   }
}
