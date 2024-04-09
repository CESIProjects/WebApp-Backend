// package resources.backend.controller.rest;

// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import jakarta.validation.Valid;
// import java.util.List;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import resources.backend.model.CustomerModel;
// import resources.backend.service.CustomerService;

// @CrossOrigin(origins = "*", allowedHeaders = "*")
// @RestController
// @RequestMapping(value = "/api/customers", produces = MediaType.APPLICATION_JSON_VALUE)
// public class CustomerRestController {

//     private final CustomerService customerService;

//     public CustomerRestController(final CustomerService customerService) {
//         this.customerService = customerService;
//     }
    
//     @GetMapping
//     public ResponseEntity<List<CustomerModel>> getAllCustomers() {
//         return ResponseEntity.ok(customerService.findAll());
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<CustomerModel> getCustomer(@PathVariable(name = "id") final Long id) {
//         return ResponseEntity.ok(customerService.get(id));
//     }

//     @PostMapping
//     @ApiResponse(responseCode = "201")
//     public ResponseEntity<Long> createCustomer(@RequestBody @Valid final CustomerModel customerDTO) {
//         final Long createdId = customerService.create(customerDTO);
//         return new ResponseEntity<>(createdId, HttpStatus.CREATED);
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<Long> updateCustomer(@PathVariable(name = "id") final Long id,
//             @RequestBody @Valid final CustomerModel customerDTO) {
//         customerService.update(id, customerDTO);
//         return ResponseEntity.ok(id);
//     }

//     @DeleteMapping("/{id}")
//     @ApiResponse(responseCode = "204")
//     public ResponseEntity<Void> deleteCustomer(@PathVariable(name = "id") final Long id) {
//         customerService.delete(id);
//         return ResponseEntity.noContent().build();
//     }

// }
