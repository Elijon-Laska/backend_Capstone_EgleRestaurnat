package backend_Capstone_EgleRestaurnat.menu.categorie;

import backend_Capstone_EgleRestaurnat.menu.piatti.Dish;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private CategoryType categoryType; // Enum per le categorie fisse

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Dish> dishes; // Relazione con i piatti

    public Category(CategoryType categoryType) {
        this.categoryType = categoryType;
    }
}
