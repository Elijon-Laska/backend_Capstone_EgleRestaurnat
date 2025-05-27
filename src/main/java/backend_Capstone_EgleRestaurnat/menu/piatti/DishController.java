package backend_Capstone_EgleRestaurnat.menu.piatti;

import backend_Capstone_EgleRestaurnat.menu.categorie.CategoryService;
import backend_Capstone_EgleRestaurnat.menu.categorie.CategoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    // Recupera tutti i piatti
    @GetMapping
    public ResponseEntity<List<DishResponse>> getAllDishes() {
        List<DishResponse> dishResponses = dishService.getAllDishes().stream()
                .map(dish -> new DishResponse(dish.getId(), dish.getName(), dish.getComposition(),
                        dish.getPrice(), dish.getImageUrl(), dish.getCategory().getCategoryType()))
                .toList();
        return ResponseEntity.ok(dishResponses);
    }

    // Recupera piatti per categoria
    @GetMapping("/category/{categoryType}")
    public ResponseEntity<List<DishResponse>> getDishesByCategory(@PathVariable CategoryType categoryType) {
        List<DishResponse> dishes = dishService.getDishesByCategory(categoryService.getCategoryByType(categoryType))
                .stream()
                .map(dish -> new DishResponse(dish.getId(), dish.getName(), dish.getComposition(),
                        dish.getPrice(), dish.getImageUrl(), dish.getCategory().getCategoryType()))
                .toList();
        return ResponseEntity.ok(dishes);
    }

    // Crea un nuovo piatto
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<DishResponse> createDish(@RequestPart("dish") DishRequest dishRequest,
                                                   @RequestPart("image") MultipartFile image) throws IOException {
        Dish newDish = dishService.createDish(dishRequest, image);
        return ResponseEntity.ok(new DishResponse(newDish.getId(), newDish.getName(), newDish.getComposition(),
                newDish.getPrice(), newDish.getImageUrl(), newDish.getCategory().getCategoryType()));
    }

    // Aggiorna un piatto
    @PutMapping("/{id}")
    public ResponseEntity<DishResponse> updateDish(@PathVariable Long id,
                                                   @RequestPart("dish") DishRequest dishRequest,
                                                   @RequestPart("image") MultipartFile image) throws IOException {
        Dish updatedDish = dishService.updateDish(id, dishRequest, image);
        return ResponseEntity.ok(new DishResponse(updatedDish.getId(), updatedDish.getName(), updatedDish.getComposition(),
                updatedDish.getPrice(), updatedDish.getImageUrl(), updatedDish.getCategory().getCategoryType()));
    }

    // Elimina un piatto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return ResponseEntity.ok("Piatto eliminato con successo");
    }
}