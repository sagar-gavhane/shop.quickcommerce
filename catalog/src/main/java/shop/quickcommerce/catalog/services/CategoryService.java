package shop.quickcommerce.catalog.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import shop.quickcommerce.Shared.dtos.CategoryDto;
import shop.quickcommerce.catalog.entities.Category;
import shop.quickcommerce.catalog.repositories.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<CategoryDto> getCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();
    }

    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));

        return modelMapper.map(category, CategoryDto.class);
    }
}
