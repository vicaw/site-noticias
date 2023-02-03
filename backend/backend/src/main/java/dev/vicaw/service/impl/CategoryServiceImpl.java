package dev.vicaw.service.impl;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.github.slugify.Slugify;

import dev.vicaw.service.CategoryService;

import dev.vicaw.exception.ApiException;
import dev.vicaw.model.category.Category;
import dev.vicaw.model.category.CategoryMapper;
import dev.vicaw.model.category.input.CategoryInput;

import dev.vicaw.repository.CategoryRepository;

@RequestScoped
public class CategoryServiceImpl implements CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        return categoryRepository.listAll();
    }

    @Override
    public Category getById(Long id) {
        Optional<Category> category = categoryRepository.findByIdOptional(id);

        if (category.isEmpty())
            throw new ApiException(404, "Essa categoria não existe.");

        return category.get();
    }

    @Override
    public Category getBySlug(String slug) {
        Optional<Category> category = categoryRepository.findBySlug(slug);

        if (category.isEmpty())
            throw new ApiException(404, "Essa categoria não existe.");

        return category.get();
    }

    @Transactional
    @Override
    public Category create(CategoryInput input) {
        Category category = categoryMapper.toModel(input);

        Slugify slugify = Slugify.builder().build();
        String slug = slugify.slugify(category.getName());
        category.setSlug(slug);

        if (categoryRepository.findBySlug(category.getSlug()).isPresent())
            throw new ApiException(409, "A categoria com o nome '" + category.getName() + "' já existe.");

        categoryRepository.persist(category);

        return category;

    }

    @Transactional
    @Override
    public Category update(Long id, CategoryInput input) {
        Category category = getById(id);

        categoryMapper.updateEntityFromInput(input, category);

        Slugify slugify = Slugify.builder().build();
        String slug = slugify.slugify(input.getName());

        if (categoryRepository.findBySlug(slug).isPresent())
            throw new ApiException(409, "A categoria com o nome '" + category.getName() + "' já existe.");

        category.setSlug(slug);

        return category;

    }

    @Transactional
    @Override
    public void delete(Long id) {
        Category category = getById(id);
        categoryRepository.delete(category);
    }

}
