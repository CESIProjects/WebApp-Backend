// package resources.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import resources.backend.entity.Customer;
import resources.backend.model.CustomerModel;
import resources.backend.repos.CustomerRepos;
import resources.backend.util.NotFoundException;

class CustomerServiceTest {

    @Mock
    private CustomerRepos customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private CustomerModel customerModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize customer
        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setPassword("password");

        // Initialize customerModel
        customerModel = new CustomerModel();
        customerModel.setName("Jane Doe");
        customerModel.setEmail("jane@example.com");
        customerModel.setPassword("password");
    }

    // TO DEBUG:
/*     @Test
    void testFindAll() {
        // Given
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());
        doReturn(customers).when(customerRepository).findAll();

        // When
        List<CustomerModel> result = customerService.findAll();

        // Then
        assertEquals(2, result.size());
    } */

    @Test
    void testGetExistingCustomer() {
        // Given
        Long customerId = 1L;
        doReturn(Optional.of(customer)).when(customerRepository).findById(customerId);

        // When
        CustomerModel result = customerService.get(customerId);

        // Then
        assertEquals(customer.getName(), result.getName());
        assertEquals(customer.getEmail(), result.getEmail());
    }

    @Test
    void testGetNonExistingCustomer() {
        // Given
        Long customerId = 1L;
        doReturn(Optional.empty()).when(customerRepository).findById(customerId);

        // When & Then
        assertThrows(NotFoundException.class, () -> customerService.get(customerId));
    }

    @Test
    void testCreateCustomer() {
        // Given
        doReturn(false).when(customerRepository).existsByEmail(customerModel.getEmail());
        doReturn(customer).when(customerRepository).save(any());
        doReturn("encodedPassword").when(passwordEncoder).encode(customerModel.getPassword());

        // When
        Long customerId = customerService.create(customerModel);

        // Then
        assertEquals(customer.getId(), customerId);
    }

    @Test
    void testCreateCustomerWithDuplicateEmail() {
        // Given
        doReturn(true).when(customerRepository).existsByEmail(customerModel.getEmail());

        // When & Then
        assertThrows(CustomerService.DuplicateEmailException.class, () -> customerService.create(customerModel));
    }

    @Test
    void testUpdateCustomer() {
        // Given
        Long customerId = 1L;
        doReturn(Optional.of(customer)).when(customerRepository).findById(customerId);
        customerModel.setName("Updated Name");
        customerModel.setEmail("updated@example.com");

        // When
        customerService.update(customerId, customerModel);

        // Then
        assertEquals(customerModel.getName(), customer.getName());
        assertEquals(customerModel.getEmail(), customer.getEmail());
    }

    @Test
    void testDeleteCustomer() {
        // Given
        Long customerId = 1L;
        doNothing().when(customerRepository).deleteById(customerId);

        // When
        customerService.delete(customerId);

        // Then
        verify(customerRepository).deleteById(customerId);
    }
}
