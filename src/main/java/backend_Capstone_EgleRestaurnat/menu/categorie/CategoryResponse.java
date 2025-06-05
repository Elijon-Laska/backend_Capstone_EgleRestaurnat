package backend_Capstone_EgleRestaurnat.menu.categorie;

import lombok.Data;
import java.util.List;

@Data
public class CategoryResponse {
    private Long id;
    private CategoryType categoryType;
    private List<String> dishes;

    public CategoryResponse(Long id, CategoryType categoryType, List<String> dishes) {
        this.id = id;
        this.categoryType = categoryType;
        this.dishes = dishes;
    }
}