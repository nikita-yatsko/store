package com.store.controllers;

import com.store.models.Product;
import com.store.models.User;
import com.store.repo.ProductRepository;
import com.store.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

/**
 * Контроллер для управления продуктами в интернет-магазине.
 */
@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public MainController(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    /**
     * Отображает главную страницу с перечнем всех продуктов.
     *
     * @param model модель для передачи данных в представление
     * @return имя представления для главной страницы
     */
    @GetMapping("/")
    public String mainMethod(Model model) {
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        logger.info("Отображение главной страницы с {} продуктами.", ((Collection<?>) products).size());
        return "main";
    }

    /**
     * Отображает страницу о продукте по его ID.
     *
     * @param id    идентификатор продукта
     * @param model модель для передачи данных в представление
     * @return имя представления для страницы о продукте или перенаправление на главную страницу, если продукт не найден
     */
    @GetMapping("/about-product/{id}")
    public String aboutProduct(@PathVariable(value = "id") long id, Model model) {
        return productRepository.findById(id)
                .map(product -> {
                    model.addAttribute("product", product); // Добавляем продукт в модель
                    logger.info("Продукт с ID {} найден и добавлен в модель.", id);
                    return "about-product";
                })
                .orElseGet(() -> {
                    logger.warn("Продукт с ID {} не найден. Перенаправление на главную страницу.", id);
                    return "redirect:/"; // Перенаправляем на главную страницу, если продукт не найден
                });
    }

    /**
     * Получает изображение продукта по его ID.
     *
     * @param id идентификатор продукта
     * @return изображение продукта в виде байтового массива или NOT FOUND, если продукт не найден
     */
    @GetMapping("/product/image/{id}")
    @ResponseBody
    public ResponseEntity<?> getProductImage(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    if (product.getImage() != null) {
                        logger.info("Изображение продукта с ID {} успешно получено.", id);
                        return ResponseEntity.ok()
                                .contentType(MediaType.IMAGE_JPEG)
                                .body(product.getImage());
                    }
                    logger.warn("Изображение для продукта с ID {} отсутствует.", id);
                    return ResponseEntity.notFound().build();
                })
                .orElseGet(() -> {
                    logger.warn("Продукт с ID {} не найден при запросе изображения.", id);
                    return ResponseEntity.notFound().build();
                });
    }

    //###########################################################

    @PostMapping("/add-item-to-cart/{id}")
    public String addItemToCart(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return "redirect:/sign-in"; // Если пользователь не авторизован, перенаправляем на страницу входа
        }

//        // Если корзина еще не инициализирована, создаем новую
//        Optional.ofNullable(user.getCart());
//        if (user.getCart() == null) {
//            user.setCart(new ArrayList<>());
//        }

        // Добавляем товар в корзину, если его там нет
//        List<Product> cart = user.getCart();
        user.addToCart(productRepository.findById(id).get());
//        if (!cart.contains(id)) {
//            cart.add(id);
//        }

        // Сохраняем изменения пользователя
        userRepository.save(user);

        // Логируем добавление товара
        logger.info("В корзину пользователя {} добавлен товар с ID {}", user.getUsername(), id);

        // Перенаправляем на главную страницу
        return "redirect:/";
    }

    @GetMapping("/product-cart-list")
    public String productCartList(Model model, Principal principal){
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            logger.info("Пользовтеля с таким именем не существует");
            return "redirect:/sign-in"; // Перенаправление на страницу входа, если пользователь не авторизован
        }

       // List<Long> cartItems = user.getCart(); // Получаем список ID товаров из корзины
        List<Product> cart = user.getCart();
//        List<Product> productsInCart = new ArrayList<>();

//        // Получаем все товары, которые есть в корзине пользователя
//        for (Product product : cart) {
//            if (product != null) {
//                productsInCart.add(product); // Добавляем товар в список
//            }
//        }

        model.addAttribute("productsInCart", cart);


        return "cart-list";
    }
}