package backend_Capstone_EgleRestaurnat.menu.categorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryType(CategoryType categoryType);
    boolean existsByCategoryType(CategoryType categoryType);
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.dishes")
    List<Category> findAllWithDishes();
}
