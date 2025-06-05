package backend_Capstone_EgleRestaurnat.menu.piatti;

import backend_Capstone_EgleRestaurnat.menu.categorie.CategoryType;
import lombok.Data;

@Data
public class DishRequest {
    private String name;
    private String composition;
    private double price;
    private String imageUrl;
    private CategoryType categoryType;

}