package backend_Capstone_EgleRestaurnat.menu.piatti;

import backend_Capstone_EgleRestaurnat.menu.categorie.Category;
import backend_Capstone_EgleRestaurnat.menu.categorie.CategoryService;
import backend_Capstone_EgleRestaurnat.menu.categorie.CategoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
public class DishRunner implements ApplicationRunner {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void run(ApplicationArguments args) {
        if (dishRepository.count() == 0) {
            addDefaultDishes();
        }
    }

    private void addDefaultDishes() {
        createBruschetta();
        createCaprese();
        createCarpaccio();
        createOliveAscolane();
        createLasagna();
        createSpaghettiCarbonara();
        createRisottoFunghi();
        createGnocchiPesto();
        createTagliataDiManzo();
        createOrataForno();
        createCotolettaMilanese();
        createPolloDiavola();
    }
    private void createBruschetta() {
        Category category = categoryService.getCategoryByType(CategoryType.ANTIPASTI);
        Dish dish = new Dish("Bruschetta al Pomodoro",
                "Pane, pomodoro, basilico, olio d'oliva",
                5.50,
                "https://res.cloudinary.com/dwtondyfy/image/upload/v1748358404/Bruschetta_qlbklf.jpg",
                category);
        dishRepository.save(dish);

    }
    private void createCaprese() {
        Category category = categoryService.getCategoryByType(CategoryType.ANTIPASTI);
        Dish dish = new Dish("Caprese",
                "Mozzarella di bufala, pomodoro, basilico",
                7.00,
                "https://res.cloudinary.com/dwtondyfy/image/upload/v1748358403/Caprese_sg8hll.avif",
                category);
        dishRepository.save(dish);
    }

    private void createCarpaccio() {
        Category category = categoryService.getCategoryByType(CategoryType.ANTIPASTI);
        Dish dish = new Dish("Carpaccio di Manzo",
                "Manzo, rucola, parmigiano, olio d'oliva",
                9.50,
                "https://res.cloudinary.com/dwtondyfy/image/upload/v1748358403/Carpaccio_rck1wo.jpg",
                category);
        dishRepository.save(dish);
    }

    private void createOliveAscolane() {
        Category category = categoryService.getCategoryByType(CategoryType.ANTIPASTI);
        Dish dish = new Dish("Olive Ascolane",
                "Olive, carne, pangrattato",
                6.00,
                "https://res.cloudinary.com/dwtondyfy/image/upload/v1748358403/OliveAscolane_u7wq0x.jpg",
                category);
        dishRepository.save(dish);
    }

    private void createLasagna() {
        Category category = categoryService.getCategoryByType(CategoryType.PRIMI);
        Dish dish = new Dish("Lasagna",
                "Pasta, ragù, besciamella, parmigiano",
                12.50,
                "https://res.cloudinary.com/dwtondyfy/image/upload/v1748358472/Lasagna_usyaqz.jpg",
                category);
        dishRepository.save(dish);
    }

    private void createSpaghettiCarbonara() {
        Category category = categoryService.getCategoryByType(CategoryType.PRIMI);
        Dish dish = new Dish("Spaghetti alla Carbonara",
                "Pasta, guanciale, uova, pecorino",
                10.00,
                "https://res.cloudinary.com/dwtondyfy/image/upload/v1748358473/SpaghettiAllaCarbonara_oojdzo.avif",
                category);
        dishRepository.save(dish);
    }

    private void createRisottoFunghi() {
        Category category = categoryService.getCategoryByType(CategoryType.PRIMI);
        Dish dish = new Dish("Risotto ai Funghi",
                "Riso, funghi porcini, parmigiano, brodo",
                11.50,
                "https://res.cloudinary.com/dwtondyfy/image/upload/v1748358472/RisottoAiFunghi_idrcdc.jpg",
                category);
        dishRepository.save(dish);
    }

    private void createGnocchiPesto() {
        Category category = categoryService.getCategoryByType(CategoryType.PRIMI);
        Dish dish = new Dish("Gnocchi al Pesto",
                "Gnocchi, basilico, pinoli, parmigiano",
                9.00,
                "https://res.cloudinary.com/dwtondyfy/image/upload/v1748358471/GnocchiAlPesto_bvukff.jpg",
                category);
        dishRepository.save(dish);
    }

    private void createTagliataDiManzo() {
        Category category = categoryService.getCategoryByType(CategoryType.SECONDI);
        Dish dish = new Dish("Tagliata di Manzo",
                "Manzo, rucola, grana, sale grosso",
                18.00,
                "https://res.cloudinary.com/dwtondyfy/image/upload/v1748358533/TagliataDiManzo_jrymxc.avif",
                category);
        dishRepository.save(dish);
    }

    private void createOrataForno() {
        Category category = categoryService.getCategoryByType(CategoryType.SECONDI);
        Dish dish = new Dish("Orata al Forno",
                "Orata, patate, rosmarino, vino bianco",
                16.50,
                "https://res.cloudinary.com/dwtondyfy/image/upload/v1748358532/OrataAlForno_yduvs5.jpg",
                category);
        dishRepository.save(dish);
    }

    private void createCotolettaMilanese() {
        Category category = categoryService.getCategoryByType(CategoryType.SECONDI);
        Dish dish = new Dish("Cotoletta alla Milanese",
                "Vitello, pangrattato, burro",
                14.00,
                "https://res.cloudinary.com/dwtondyfy/image/upload/v1748358531/CotolettaAllaMilanese_bkqcew.jpg",
                category);
        dishRepository.save(dish);
    }

    private void createPolloDiavola() {
        Category category = categoryService.getCategoryByType(CategoryType.SECONDI);
        Dish dish = new Dish("Pollo alla Diavola",
                "Pollo, peperoncino, paprika, olio d'oliva",
                13.50,
                "https://res.cloudinary.com/dwtondyfy/image/upload/v1748358532/PolloAllaDiavola_vn1urv.jpg",
                category);
        dishRepository.save(dish);
    }


}