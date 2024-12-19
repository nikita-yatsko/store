package com.electronicsstore.store.controllers;

import com.electronicsstore.store.models.Product;
import com.electronicsstore.store.models.User;
import com.electronicsstore.store.repo.ProductRepository;
import com.electronicsstore.store.repo.UserRepository;
import com.electronicsstore.store.services.ProductDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

/**
 * Контроллер для управления продуктами в интернет-магазине.
 */
@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final ProductRepository productRepository;
    private final ProductDataService productDataService;
    private final UserRepository userRepository;

    @Autowired
    public MainController(ProductRepository productRepository, UserRepository userRepository, ProductDataService productDataService) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productDataService = productDataService;
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
        model.addAttribute("buttonText", "В корзину");
        model.addAttribute("formActionPrefix", "/add-item-to-cart/");
        model.addAttribute("pathToLink", "/about-product/");

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

    /**
     * Принимает данные для добавления товара в корзину.
     *
     * @param id идентификатор продукта
     * @return имя представления для главной страницы
     */
    @PostMapping("/add-item-to-cart/{id}")
    public String addItemToCart(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return "redirect:/sign-in"; // Если пользователь не авторизован, перенаправляем на страницу входа
        }
        user.addToCart(productRepository.findById(id).get());
        // Сохраняем изменения пользователя
        userRepository.save(user);

        // Логируем добавление товара
        logger.info("В корзину пользователя {} добавлен товар с ID {}", user.getUsername(), id);
        return "redirect:/";
    }


    /**
     * Отображает страницу с добавленными в корзину товарами.
     *
     * @param model модель для передачи данных в представление
     * @return имя представления для корзины
     */
    @GetMapping("/product-cart-list")
    public String productCartList(Model model, Principal principal){
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            logger.info("Пользовтеля с таким именем не существует");
            return "redirect:/sign-in"; // Перенаправление на страницу входа, если пользователь не авторизован
        }

        List<Product> cart = user.getCart();
        logger.info("Колличество товаров в корзине у пользователя {} равно {}", user.getUsername(), cart.size());

        model.addAttribute("buttonText", "Удалить из корзины");
        model.addAttribute("formActionPrefix", "/remove-from-cart/");
        model.addAttribute("totalPrice", cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("totalCount", cart.size());
        model.addAttribute("productsInCart", cart);

        return "cart-list";
    }

    /**
     * Принимает данные для удаления товара из корзины.
     *
     * @param id идентификатор продукта
     * @return имя представления для страницы с добавленными товарами
     */
    @PostMapping("/remove-from-cart/{id}")
    public String removeFromCart(@PathVariable Long id, Principal principal){
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        Optional<Product> productOptional = productRepository.findById(id);
        Product product = productOptional.get();

        user.removeFromCart(product);
        userRepository.save(user);
        logger.info("Продукт с индексом {} удалён из корзины", id);

        return "redirect:/product-cart-list";
    }


    /**
     * Очищает корзину после успешного оформления заказа.
     *
     * @param principal сущность пользователя
     * @return имя представления для главной страницы
     */
    @GetMapping("/cart-list/delivery/clear")
    public String clearOrderDelivery(Principal principal){
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        List<Product> cart = user.getCart();
        logger.info("Пользователь {} удалил {} товара(ов) из корзины", user.getUsername(), user.getCart().size());

        for (Product product : cart) {
            product.setOwner(null);
        }
        // Очистка корзины
        cart.clear();
        userRepository.save(user);

        return "redirect:/";
    }

    /**
     * Отображает страницу пользователя.
     *
     * @param model модель для передачи данных в прелставление.
     * @return имя представления для страницы пользователя.
     */
    @GetMapping("/user-window")
    public String userWindow(Principal principal, Model model){
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        model.addAttribute("userName", user.getUsername());
        model.addAttribute("cartItemCount", user.getCart().size());

        return "user-window";
    }


    /**
     * Принимает данные для изменеия характеристик товара.
     *
     * @param id идентификатор продукта
     *  @param newProduct измененный продукт
     * @return имя представления для главной страницы администатора
     */
    @PostMapping("/product/update/{id}")
    public String updateProduct(@PathVariable Long id, Product newProduct){
        Optional<Product> productOptional = productRepository.findById(id);
        Product productOld = productOptional.get();
        newProduct.setImage(productOld.getImage());

        productRepository.save(newProduct);
        productRepository.deleteById(id);

        logger.info("Был удален продукт с ID {} и вместо него добавлен продукт с ID", id, newProduct.getProduct_id());

        return "redirect:/main-admin";
    }

    /**
     * Принимает данные для изменеия характеристик товара.

     * @return имя представления для главной страницы администатора
     */
    @GetMapping("/cart-list/delivery")
    public String orderDelivery(){ return "delivery"; }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/store/oplata")
    public String oplataWindow(){ return "oplata"; }

    @GetMapping("/store/about")
    public String aboutCompany(){ return "about-company"; }

    @GetMapping("/store/contacts")
    public String contacts(){ return "contacts"; }

    @GetMapping("/store/about-delivery")
    public String aboutDelivery(){ return "about-delivery"; }

    @GetMapping("/store/bonus")
    public String bonus(){ return "bonus"; }

    @GetMapping("/register")
    public String Register(){ return "register"; }

}