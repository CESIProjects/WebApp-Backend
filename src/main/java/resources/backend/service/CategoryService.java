package resources.backend.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import resources.backend.entity.Category;
import resources.backend.model.CategoryModel;
import resources.backend.repos.CategoryRepos;
import resources.backend.util.NotFoundException;

@Service
public class CategoryService {

    private final CategoryRepos categoryRepository;

    public CategoryService(final CategoryRepos categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryModel> findAll() {
        final List<Category> categories = categoryRepository.findAll(Sort.by("id"));
        
        return categories.stream()
                .map(category -> mapToDTO(category, new CategoryModel()))
                .toList();
    }
      

    public CategoryModel get(final Long id) {
        return categoryRepository.findById(id)
                .map(category -> mapToDTO(category, new CategoryModel()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CategoryModel categoryDTO) {
        final Category category = new Category();
        mapToEntity(categoryDTO, category);

        return categoryRepository.save(category).getId();
    }

    public void update(final Long id, final CategoryModel categoryDTO) {
        final Category category = categoryRepository.findById(id)
            .orElseThrow(NotFoundException::new);
        mapToEntity(categoryDTO, category);
        categoryRepository.save(category);
    }


    public void delete(final Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryModel mapToDTO(final Category category, final CategoryModel categoryDTO) {
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    private Category mapToEntity(final CategoryModel categoryDTO, final Category category) {
        category.setName(categoryDTO.getName());
        return category;
    }
}