package shop.quickcommerce.catalog.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.quickcommerce.Shared.dtos.ProductDto;
import shop.quickcommerce.catalog.entities.Brand;
import shop.quickcommerce.catalog.entities.Category;
import shop.quickcommerce.catalog.entities.Product;
import shop.quickcommerce.catalog.mappers.ProductMapper;
import shop.quickcommerce.catalog.repositories.BrandRepository;
import shop.quickcommerce.catalog.repositories.CategoryRepository;
import shop.quickcommerce.catalog.repositories.ProductRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;
    private final ProductMapper productMapper;
    private final EntityManager entityManager;

    public ProductService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            BrandRepository brandRepository,
            ModelMapper modelMapper,
            ProductMapper productMapper,
            EntityManager entityManager
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
        this.productMapper = productMapper;
        this.entityManager = entityManager;
    }

    public List<ProductDto> getProducts(Optional<Integer> page, Optional<Integer> pageSize) {
        Page<Product> products = productRepository.findAll(PageRequest.of(page.orElse(0), pageSize.orElse(10)));

        return products.getContent().stream()
                .map(product -> {
                    ProductDto productDto = modelMapper.map(product, ProductDto.class);

                    return productDto;
                })
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
                .findById(productDto.getCategory().getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + productDto.getCategory().getCategoryId()));

        Brand brand = brandRepository
                .findById(productDto.getBrand().getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("Brand not found with id: " + productDto.getBrand().getBrandId()));

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

    public Map<String, Object> searchProductsByNameOrDescription(String query) {
        List<Product> products = productRepository.searchByNameOrDescription(query);
        List<ProductDto> productDtos = products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();

        return Map.of("products", productDtos, "total", productDtos.size());
    }

    public Map<String, Object> advanceSearch(String query, Integer page, Integer pageSize) {
        Pageable pageable = Pageable.ofSize(pageSize).withPage(page);

        Integer offset = (int) pageable.getOffset();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

        Root<Product> root = criteriaQuery.from(Product.class);

        Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + query + "%");
        Predicate descriptionPredicate = criteriaBuilder.like(root.get("description"), "%" + query + "%");

        criteriaQuery.where(criteriaBuilder.or(namePredicate, descriptionPredicate));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        Integer total = typedQuery.getResultList().size();

        List<Product> products = typedQuery.setFirstResult(offset).setMaxResults(pageSize).getResultList();

        List<ProductDto> productDtos = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();

        return Map.of("products", productDtos, "total", total);
    }

    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        productMapper.updateUserFromDto(modelMapper.map(productDto, Product.class), product);

        System.out.println(product);

        return productDto;
    }
}
