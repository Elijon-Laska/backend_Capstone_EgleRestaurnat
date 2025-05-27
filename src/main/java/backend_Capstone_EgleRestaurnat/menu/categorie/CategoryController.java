package backend_Capstone_EgleRestaurnat.menu.categorie;

import backend_Capstone_EgleRestaurnat.menu.piatti.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Recupera tutte le categorie
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categoryResponses = categoryService.getAllCategories().stream()
                .map(category -> new CategoryResponse(category.getId(), category.getCategoryType(),
                        category.getDishes().stream().map(Dish::getName).toList()))
                .toList();
        return ResponseEntity.ok(categoryResponses);
    }

    // Recupera una categoria per tipo
    @GetMapping("/type/{categoryType}")
    public ResponseEntity<CategoryResponse> getCategoryByType(@PathVariable CategoryType categoryType) {
        Category category = categoryService.getCategoryByType(categoryType);
        return ResponseEntity.ok(new CategoryResponse(category.getId(), category.getCategoryType(),
                category.getDishes().stream().map(Dish::getName).toList()));
    }

    // Crea una nuova categoria
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        Category newCategory = categoryService.createCategory(categoryRequest.getCategoryType());
        return ResponseEntity.ok(new CategoryResponse(newCategory.getId(), newCategory.getCategoryType(), List.of()));
    }

    // Elimina una categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Categoria eliminata con successo");
    }
}