package resources.backend.controller.rest;
import jakarta.validation.Valid;
import resources.backend.model.CategoryModel;
import resources.backend.service.CategoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryRestController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryModel>> getAllCategory() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryModel> getCategory(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(categoryService.get(id));
    }

    @PostMapping("/post")
    public ResponseEntity<CategoryModel> createCategory(@Valid @RequestBody CategoryModel categoryDTO) {
        Long categoryId = categoryService.create(categoryDTO);
        // Retrieve the newly created category and return it with status code 201
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.get(categoryId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryModel> updateCategory(@PathVariable(name = "id") final Long id,
        @Valid @RequestBody CategoryModel categoryDTO) {
        categoryService.update(id, categoryDTO);
        // Retrieve the updated category and return it
        return ResponseEntity.ok(categoryService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id") final Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
