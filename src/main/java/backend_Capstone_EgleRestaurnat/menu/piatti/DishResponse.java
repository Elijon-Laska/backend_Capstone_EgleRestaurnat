package backend_Capstone_EgleRestaurnat.menu.piatti;

import backend_Capstone_EgleRestaurnat.menu.categorie.CategoryType;
import lombok.Data;

@Data
public class DishResponse {
    private Long id;
    private String name;
    private String composition;
    private double price;
    private String imageUrl;
    private CategoryType categoryType;

    public DishResponse(Long id, String name, String composition, double price, String imageUrl, CategoryType categoryType) {
        this.id = id;
        this.name = name;
        this.composition = composition;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryType = categoryType;
    }
}