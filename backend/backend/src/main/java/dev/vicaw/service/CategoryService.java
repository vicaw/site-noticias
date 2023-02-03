package dev.vicaw.service;

import java.util.List;

import dev.vicaw.model.category.Category;
import dev.vicaw.model.category.input.CategoryInput;

public interface CategoryService {
    public List<Category> list();

    public Category create(CategoryInput input);

    public Category update(Long id, CategoryInput input);

    public void delete(Long id);

    public Category getById(Long id);

    public Category getBySlug(String slug);
}
