package shop.quickcommerce.CatalogService.services;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import shop.quickcommerce.CatalogService.entities.Brand;
import shop.quickcommerce.CatalogService.entities.Category;
import shop.quickcommerce.CatalogService.entities.Product;
import shop.quickcommerce.CatalogService.repositories.BrandRepository;
import shop.quickcommerce.CatalogService.repositories.CategoryRepository;
import shop.quickcommerce.CatalogService.repositories.ProductRepository;
import shop.quickcommerce.Shared.dtos.ProductDto;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    public ProductService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            BrandRepository brandRepository,
            ModelMapper modelMapper
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProductDto> getProducts(Optional<Integer> page, Optional<Integer> pageSize) {
        Page<Product> products = productRepository.findAll(PageRequest.of(page.orElse(0), pageSize.orElse(10)));

        return products.getContent().stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    public ProductDto getProductById(Long productId) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        return modelMapper.map(product, ProductDto.class);
    }

    @Transactional
    public ProductDto addProduct(ProductDto productDto) {
        Category category = categoryRepository
                .findById(productDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + productDto.getCategoryId()));

        Brand brand = brandRepository
                .findById(productDto.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("Brand not found with id: " + productDto.getBrandId()));

        Product product = modelMapper.map(productDto, Product.class);

        product.setCategory(category);
        product.setBrand(brand);

        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    public List<ProductDto> getProductsByCategoryId(Long categoryId) {
        List<Product> products = productRepository
                .findByCategoryId(categoryId);

        return products
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    public List<ProductDto> getProductsByBrandId(Long brandId) {
        List<Product> products = productRepository
                .findByBrandId(brandId);

        return products
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }
}
