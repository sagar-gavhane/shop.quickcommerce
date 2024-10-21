package shop.quickcommerce.catalogservice.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import shop.quickcommerce.Shared.dtos.ProductDto;
import shop.quickcommerce.catalogservice.services.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("should return all products")
    void givenProducts_whenGetProducts_thenReturnAllProducts() throws Exception {
        List<ProductDto> products = new ArrayList<>();

        products.add(
                ProductDto.builder()
                        .productId(1L)
                        .name("test")
                        .marketPrice(100.0f)
                        .salePrice(80.0f)
                        .description("test")
                        .brandId(1L)
                        .categoryId(1L)
                        .build()
        );

        given(productService.getProducts(any(Optional.class), any(Optional.class)))
                .willReturn(products);

        ResultActions response = mockMvc.perform(get("/api/catalogs/products"));

        response.andDo(MockMvcResultHandlers.print());
    }
}