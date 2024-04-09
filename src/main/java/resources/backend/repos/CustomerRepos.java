package resources.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import resources.backend.entity.Customer;

public interface CustomerRepos extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);
}

