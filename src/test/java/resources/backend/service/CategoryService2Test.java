package resources.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import resources.backend.entity.Category;
import resources.backend.model.CategoryModel;
import resources.backend.repos.CategoryRepos;
import resources.backend.util.NotFoundException;

class CategoryService2Test {

    @Mock
    private CategoryRepos categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryModel categoryModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize category
        category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        // Initialize categoryModel
        categoryModel = new CategoryModel();
        categoryModel.setName("Test Category");
    }

    // TO DEBUG:
/*     @Test
    void testFindAll() {
        // Given
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        categories.add(new Category());
        doReturn(categories).when(categoryRepository).findAll();

        // When
        List<CategoryModel> result = categoryService.findAll();

        // Then
        assertEquals(2, result.size());
    } */

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