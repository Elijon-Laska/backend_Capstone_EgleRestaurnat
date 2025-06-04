package backend_Capstone_EgleRestaurnat.menu.categorie;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Crea una nuova categoria
    public Category createCategory(CategoryType categoryType) {
        if (categoryRepository.existsByCategoryType(categoryType)) {
            throw new EntityExistsException("La categoria '" + categoryType + "' esiste già.");
        }
        Category category = new Category(categoryType);
        return categoryRepository.save(category);
    }

    // Recupera tutte le categorie
    public List<Category> getAllCategories() {
        return categoryRepository.findAllWithDishes();
    }

    // Trova una categoria per tipo
    public Category getCategoryByType(CategoryType categoryType) {
        return categoryRepository.findByCategoryType(categoryType)
                .orElseThrow(() -> new EntityNotFoundException("Categoria '" + categoryType + "' non trovata."));
    }

    // Elimina una categoria
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria con ID " + id + " non trovata."));
        categoryRepository.delete(category);
    }

}