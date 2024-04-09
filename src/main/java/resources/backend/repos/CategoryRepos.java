package resources.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import resources.backend.entity.Category;

public interface CategoryRepos extends JpaRepository<Category, Long> {
    
}
