package shop.quickcommerce.catalog.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.quickcommerce.Shared.dtos.ProductDto;
import shop.quickcommerce.catalog.services.ProductService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/catalogs/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts(
            @RequestParam(required = false, name = "page", defaultValue = "0") Optional<Integer> page,
            @RequestParam(required = false, name = "pageSize", defaultValue = "10") Optional<Integer> pageSize
    ) {
        List<ProductDto> products = productService.getProducts(page, pageSize);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductDto> getProductsByCategoryId(@PathVariable("categoryId") Long categoryId) {
        List<ProductDto> products = productService.getProductsByCategoryId(categoryId);
        return products;
    }

    @GetMapping("/brand/{brandId}")
    public List<ProductDto> getProductsByBrandId(@PathVariable("brandId") Long brandId) {
        List<ProductDto> products = productService.getProductsByBrandId(brandId);
        return products;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long productId) {
        ProductDto product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchProductsByNameOrDescription(@RequestParam("query") String query) {
        Map<String, Object> result = productService.searchProductsByNameOrDescription(query);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/advance-search")
    public ResponseEntity<Map<String, Object>> advanceSearch(@RequestParam("query") String query, @RequestParam(name = "page", defaultValue = "0") Integer page, @RequestParam(name = "pageSize", defaultValue = "25") Integer pageSize) {
        Map<String, Object> result = productService.advanceSearch(query, page, pageSize);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        ProductDto product = productService.addProduct(productDto);
        return ResponseEntity.ok(product);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @RequestBody ProductDto productDto) {
        ProductDto product = productService.updateProduct(productId, productDto);
        return ResponseEntity.ok(product);
    }
}
