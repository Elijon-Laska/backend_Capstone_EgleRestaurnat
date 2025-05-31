package backend_Capstone_EgleRestaurnat.menu.categorie;

import backend_Capstone_EgleRestaurnat.menu.piatti.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Recupera tutte le categorie disponibili nel ristorante
    // Accessibile a tutti gli utenti (USER e ADMIN)
    // Restituisce una lista di tutte le categorie con i relativi piatti
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categoryResponses = categoryService.getAllCategories().stream()
                .map(category -> new CategoryResponse(category.getId(), category.getCategoryType(),
                        category.getDishes().stream().map(Dish::getName).toList()))
                .toList();
        return ResponseEntity.ok(categoryResponses);
    }

    // Recupera una specifica categoria per tipo
    // Accessibile a tutti gli utenti (USER e ADMIN)
    // Permette di ottenere i dettagli di una singola categoria
    @GetMapping("/type/{categoryType}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CategoryResponse> getCategoryByType(@PathVariable CategoryType categoryType) {
        Category category = categoryService.getCategoryByType(categoryType);
        return ResponseEntity.ok(new CategoryResponse(category.getId(), category.getCategoryType(),
                category.getDishes().stream().map(Dish::getName).toList()));
    }

    // Crea una nuova categoria nel menu
    // Accessibile solo agli ADMIN
    // Richiede il tipo di categoria da creare
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        Category newCategory = categoryService.createCategory(categoryRequest.getCategoryType());
        return ResponseEntity.ok(new CategoryResponse(newCategory.getId(), newCategory.getCategoryType(), List.of()));
    }

    // Elimina una categoria dal menu
    // Accessibile solo agli ADMIN
    // Rimuove la categoria e tutti i suoi piatti
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Categoria eliminata con successo");
    }
}