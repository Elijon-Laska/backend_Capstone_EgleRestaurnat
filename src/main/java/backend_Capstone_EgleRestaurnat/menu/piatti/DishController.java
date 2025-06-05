package backend_Capstone_EgleRestaurnat.menu.piatti;

import backend_Capstone_EgleRestaurnat.menu.categorie.CategoryService;
import backend_Capstone_EgleRestaurnat.menu.categorie.CategoryType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping

    public ResponseEntity<List<DishResponse>> getAllDishes() {
        List<DishResponse> dishResponses = dishService.getAllDishes().stream()
                .map(dish -> new DishResponse(dish.getId(), dish.getName(), dish.getComposition(),
                        dish.getPrice(), dish.getImageUrl(), dish.getCategory().getCategoryType()))
                .toList();
        return ResponseEntity.ok(dishResponses);
    }

    // Recupera piatti per una specifica categoria
    // Accessibile a tutti gli utenti (USER e ADMIN)
    // Permette di filtrare i piatti per tipo di categoria (antipasti, primi, secondi, ecc.)
    @GetMapping("/category/{categoryType}")

    public ResponseEntity<List<DishResponse>> getDishesByCategory(@PathVariable CategoryType categoryType) {
        List<DishResponse> dishes = dishService.getDishesByCategory(categoryService.getCategoryByType(categoryType))
                .stream()
                .map(dish -> new DishResponse(dish.getId(), dish.getName(), dish.getComposition(),
                        dish.getPrice(), dish.getImageUrl(), dish.getCategory().getCategoryType()))
                .toList();
        return ResponseEntity.ok(dishes);
    }

    // Crea un nuovo piatto nel menu
    // Accessibile solo agli ADMIN
    // Richiede dati del piatto e un'immagine
    // L'immagine viene caricata tramite Cloudinary
    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DishResponse> createDish(
            @RequestPart("dish") String dishJson,
            @RequestPart("image") MultipartFile image
    ) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        DishRequest dishRequest = mapper.readValue(dishJson, DishRequest.class);
        Dish newDish = dishService.createDish(dishRequest, image);
        return ResponseEntity.ok(new DishResponse(newDish.getId(), newDish.getName(), newDish.getComposition(),
                newDish.getPrice(), newDish.getImageUrl(), newDish.getCategory().getCategoryType()));
    }

    // Aggiorna un piatto esistente
    // Accessibile solo agli ADMIN
    // Permette di modificare i dati del piatto e/o l'immagine
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DishResponse> updateDish(@PathVariable Long id,
                                                   @RequestPart("dish") DishRequest dishRequest,
                                                   @RequestPart("image") MultipartFile image) throws IOException {
        Dish updatedDish = dishService.updateDish(id, dishRequest, image);
        return ResponseEntity.ok(new DishResponse(updatedDish.getId(), updatedDish.getName(), updatedDish.getComposition(),
                updatedDish.getPrice(), updatedDish.getImageUrl(), updatedDish.getCategory().getCategoryType()));
    }

    // Elimina un piatto dal menu
    // Accessibile solo agli ADMIN
    // Rimuove il piatto e la sua immagine da Cloudinary
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return ResponseEntity.ok("Piatto eliminato con successo");
    }
}