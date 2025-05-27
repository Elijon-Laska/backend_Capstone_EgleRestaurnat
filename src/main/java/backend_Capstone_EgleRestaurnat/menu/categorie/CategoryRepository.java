package backend_Capstone_EgleRestaurnat.menu.categorie;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryType(CategoryType categoryType);
    boolean existsByCategoryType(CategoryType categoryType);
}