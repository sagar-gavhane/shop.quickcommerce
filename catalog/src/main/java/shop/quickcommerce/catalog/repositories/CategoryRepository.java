package shop.quickcommerce.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.quickcommerce.catalog.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
