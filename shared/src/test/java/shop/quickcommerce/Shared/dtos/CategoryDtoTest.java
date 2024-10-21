package shop.quickcommerce.Shared.dtos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryDtoTest {
    @Test
    @DisplayName("should create 'Clothing' CategoryDto")
    void givenCategoryIdAndName_thenCategoryDtoIsCreated() {
        Long categoryId = 1000l;
        String categoryName = "Clothing";

        CategoryDto categoryDto = new CategoryDto(categoryId, categoryName);

        assertEquals(categoryDto.getCategoryId(), categoryId);
        assertEquals(categoryDto.getName(), categoryName);
    }
}