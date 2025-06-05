package backend_Capstone_EgleRestaurnat.menu.piatti;


import backend_Capstone_EgleRestaurnat.menu.categorie.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByCategory(Category category);

}