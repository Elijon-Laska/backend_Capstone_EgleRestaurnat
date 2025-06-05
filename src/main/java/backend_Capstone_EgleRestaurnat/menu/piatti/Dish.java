package backend_Capstone_EgleRestaurnat.menu.piatti;

import backend_Capstone_EgleRestaurnat.menu.categorie.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dishes")

public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Nome del piatto

    @Column(nullable = false, length = 500)
    private String composition;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String imageUrl; // URL dell'immagine (gestito tramite Cloudinary)

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Dish(String name, String composition, double price, String imageUrl, Category category) {
        this.name = name;
        this.composition = composition;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }



}