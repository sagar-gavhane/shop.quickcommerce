package shop.quickcommerce.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.quickcommerce.catalog.entities.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
