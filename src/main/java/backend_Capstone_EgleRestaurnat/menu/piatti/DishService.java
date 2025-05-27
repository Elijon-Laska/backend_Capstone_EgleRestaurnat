package backend_Capstone_EgleRestaurnat.menu.piatti;

import backend_Capstone_EgleRestaurnat.menu.categorie.Category;
import backend_Capstone_EgleRestaurnat.menu.categorie.CategoryService;
import backend_Capstone_EgleRestaurnat.cloudinary.CloudinaryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CloudinaryService cloudinaryService;

    // Crea un nuovo piatto con immagine
    public Dish createDish(DishRequest dishRequest, MultipartFile image) throws IOException {
        Category category = categoryService.getCategoryByType(dishRequest.getCategoryType());
        String imageUrl = cloudinaryService.uploadImage(image);

        Dish dish = new Dish(dishRequest.getName(), dishRequest.getComposition(), dishRequest.getPrice(), imageUrl, category);
        return dishRepository.save(dish);
    }

    // Recupera tutti i piatti
    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    // Recupera piatti di una categoria
    public List<Dish> getDishesByCategory(Category category) {
        return dishRepository.findByCategory(category);
    }

    // Aggiorna un piatto
    public Dish updateDish(Long id, DishRequest dishRequest, MultipartFile image) throws IOException {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Piatto con ID " + id + " non trovato."));

        dish.setName(dishRequest.getName());
        dish.setComposition(dishRequest.getComposition());
        dish.setPrice(dishRequest.getPrice());

        if (image != null && !image.isEmpty()) {
            String newImageUrl = cloudinaryService.uploadImage(image);
            dish.setImageUrl(newImageUrl);
        }

        return dishRepository.save(dish);
    }

    // Elimina un piatto
    public void deleteDish(Long id) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Piatto con ID " + id + " non trovato."));
        dishRepository.delete(dish);
    }
}