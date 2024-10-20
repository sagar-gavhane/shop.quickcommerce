package shop.quickcommerce.CatalogService.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.quickcommerce.CatalogService.services.ProductService;
import shop.quickcommerce.Shared.dtos.ProductDto;

import java.util.List;
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
