// package resources.backend.service;

// import java.util.List;
// import org.springframework.data.domain.Sort;
// import org.springframework.stereotype.Service;

// import com.google.common.base.Optional;
// import resources.backend.entity.Customer;
// import resources.backend.model.CustomerModel;
// import resources.backend.repository.CustomerRepos;
// import resources.backend.util.NotFoundException;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;


// @Service
// public class CustomerService implements UserDetailsService  {

//     private final CustomerRepos customerRepository;
//     private final PasswordEncoder passwordEncoder;

//     public CustomerService(final CustomerRepos customerRepository, final PasswordEncoder passwordEncoder) {
//         this.customerRepository = customerRepository;
//         this.passwordEncoder = passwordEncoder;
//     }

//     public List<CustomerModel> findAll() {
//         final List<Customer> customers = customerRepository.findAll(Sort.by("id"));
//         return customers.stream()
//                 .map(customer -> mapToDTO(customer, new CustomerModel()))
//                 .toList();
//     }

//     public CustomerModel get(final Long id) {
//         return customerRepository.findById(id)
//                 .map(customer -> mapToDTO(customer, new CustomerModel()))
//                 .orElseThrow(NotFoundException::new);
//     }

//     public Long create(final CustomerModel customerDTO) {
//         if (customerRepository.existsByEmail(customerDTO.getEmail())) {
//             throw new DuplicateEmailException("L'email existe déjà");
//         }

//         final Customer customer = new Customer();
//         mapToEntity(customerDTO, customer);

//         customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));

//         return customerRepository.save(customer).getId();
//     }

//     public void update(final Long id, final CustomerModel customerDTO) {
//         final Customer customer = customerRepository.findById(id)
//             .orElseThrow(NotFoundException::new);
//         mapToEntity(customerDTO, customer);
//         customerRepository.save(customer);
//     }


//     public void delete(final Long id) {
//         customerRepository.deleteById(id);
//     }

//     private CustomerModel mapToDTO(final Customer customer, final CustomerModel customerDTO) {
//         customerDTO.setId(customer.getId());
//         customerDTO.setName(customer.getName());
//         customerDTO.setUsername(customer.getUsername());
//         customerDTO.setRole(customer.getRole());
//         customerDTO.setEmail(customer.getEmail());
//         customerDTO.setPassword(customer.getPassword());
//         return customerDTO;
//     }

//     private Customer mapToEntity(final CustomerModel customerDTO, final Customer customer) {
//         customer.setName(customerDTO.getName());
//         customer.setUsername(customerDTO.getUsername());
//         customer.setRole(customerDTO.getRole());
//         customer.setEmail(customerDTO.getEmail());
//         customer.setPassword(customerDTO.getPassword());
//         return customer;
//     }

//     class DuplicateEmailException extends RuntimeException {
//         public DuplicateEmailException(String message) {
//             super(message);
//         }
//     }

//     // @Autowired
//     // private CustomerRepos repository;

//     @Override
//     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//         Optional<Customer> user = customerRepository.findByEmail(email);
//         if (user.isPresent()) {
//             Customer userObj = user.get();
//             // Construisez et retournez UserDetails
//             return org.springframework.security.core.userdetails.User.builder()
                    
//                     .username(userObj.getEmail())
//                     .password(userObj.getPassword())
//                     .roles(getRoles(userObj))
//                     .build();
//         } else {
//             throw new UsernameNotFoundException("User not found: " + email);
//         }
//     }

//     private String[] getRoles(Customer user) {
//         if (user.getRole() == null) {
//             return new String[]{"USER"};
//         }
//         return new String[] {user.getRole()};
//     }
// }
