package resources.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import resources.backend.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);
}

