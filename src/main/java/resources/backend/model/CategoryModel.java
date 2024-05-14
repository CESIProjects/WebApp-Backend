package resources.backend.model;

import jakarta.validation.constraints.Size;

public class CategoryModel {

    private Long id;

    @Size(max = 255)
    private String name;

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }
}