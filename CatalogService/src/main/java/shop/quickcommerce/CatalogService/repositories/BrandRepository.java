package shop.quickcommerce.CatalogService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.quickcommerce.CatalogService.entities.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
