package resources.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import resources.backend.entity.Category;
import resources.backend.model.CategoryModel;
import resources.backend.repos.CategoryRepos;
import resources.backend.util.NotFoundException;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepos categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryModel categoryModel;
    private Category category1;
    private Category category2;
    private CategoryModel categoryModel1;
    private CategoryModel categoryModel2;

    @BeforeEach
    void setUp() {
        // Initialize category and category models
        category = createCategory(1L, "Test Category");
        category1 = createCategory(1L, "Category 1");
        category2 = createCategory(2L, "Category 2");
        categoryModel = createCategoryModel("Test Category");
        categoryModel1 = createCategoryModel("Category 1");
        categoryModel2 = createCategoryModel("Category 2");
    }

    // Helper methods
    private Category createCategory(Long id, String name) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }

    private CategoryModel createCategoryModel(String name) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName(name);
        return categoryModel;
    }

    @Test
    void testFindAll() {
        // Mocking the categoryRepository
        List<Category> mockCategoryList = new ArrayList<>();
        mockCategoryList.add(category1);
        mockCategoryList.add(category2);
    
        // Mocking the behavior of categoryRepository.findAll()
        when(categoryRepository.findAll(Sort.by("id"))).thenReturn(mockCategoryList);
    
        // Call the service method
        List<CategoryModel> result = categoryService.findAll();
    
        // Verify the result
        assertEquals(2, result.size());
        assertEquals(categoryModel1.getName(), result.get(0).getName());
        assertEquals(categoryModel2.getName(), result.get(1).getName());
    }

    @Test
    void testGetExistingCategory() {
        // Given
        Long categoryId = 1L;
        doReturn(Optional.of(category)).when(categoryRepository).findById(categoryId);

        // When
        CategoryModel result = categoryService.get(categoryId);

        // Then
        assertEquals(categoryId, result.getId());
    }

    @Test
    void testGetNonExistingCategory() {
        // Given
        Long categoryId = 1L;
        doReturn(Optional.empty()).when(categoryRepository).findById(categoryId);

        // When & Then
        assertThrows(NotFoundException.class, () -> categoryService.get(categoryId));
    }

    @Test
    void testCreateCategory() {
        // Given
        doReturn(category).when(categoryRepository).save(any());

        // When
        Long categoryId = categoryService.create(categoryModel);

        // Then
        assertEquals(category.getId(), categoryId);
    }

    @Test
    void testUpdateCategory() {
        // Given
        Long categoryId = 1L;
        doReturn(Optional.of(category)).when(categoryRepository).findById(categoryId);
        categoryModel.setName("Updated Category");

        // When
        categoryService.update(categoryId, categoryModel);

        // Then
        assertEquals(categoryModel.getName(), category.getName());
    }

    @Test
    void testDeleteCategory() {
        // Given
        Long categoryId = 1L;
        doNothing().when(categoryRepository).deleteById(categoryId);

        // When
        categoryService.delete(categoryId);

        // Then
        verify(categoryRepository).deleteById(categoryId);
    }
}