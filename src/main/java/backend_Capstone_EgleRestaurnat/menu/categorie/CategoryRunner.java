package backend_Capstone_EgleRestaurnat.menu.categorie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class CategoryRunner implements ApplicationRunner {

    @Autowired
    private CategoryService categoryService;

    @Override
    public void run(ApplicationArguments args) {
        if (categoryService.getAllCategories().isEmpty()) {
            createDefaultCategories();
        }
    }

    private void createDefaultCategories() {
        // Creiamo tutte le categorie definite nell'enum CategoryType
        categoryService.createCategory(CategoryType.ANTIPASTI);
        categoryService.createCategory(CategoryType.PRIMI);
        categoryService.createCategory(CategoryType.SECONDI);
        categoryService.createCategory(CategoryType.CONTORNI);
        categoryService.createCategory(CategoryType.DOLCI);

        System.out.println("Tutte le categorie predefinite sono state create nel database!");
    }
}