package shop.quickcommerce.Shared.dtos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrandDtoTest {
    @Test
    @DisplayName("should create 'Adidas' BrandDto")
    void givenBrandIdAndName_thenBrandDtoIsCreated() {
        Long brandId = 1000l;
        String brandName = "Adidas";
        BrandDto brandDto = new BrandDto(brandId, brandName);

        assertEquals(brandDto.getBrandId(), brandId);
        assertEquals(brandDto.getName(), brandName);
    }
}