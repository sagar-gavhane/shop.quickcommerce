package shop.quickcommerce.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.quickcommerce.catalog.entities.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM products p WHERE p.category_id = :categoryId")
    List<Product> findByCategoryId(Long categoryId);

    @Query(nativeQuery = true, value = "SELECT * FROM products p WHERE p.brand_id = :brandId")
    List<Product> findByBrandId(Long brandId);

    @Query(nativeQuery = true, value = "SELECT * FROM products p WHERE p.name ILIKE %:query% OR p.description ILIKE %:query%")
    List<Product> searchByNameOrDescription(String query);
}
