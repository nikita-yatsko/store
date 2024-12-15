package com.electronicsstore.store.controllers;

import com.electronicsstore.store.models.Product;
import com.electronicsstore.store.models.User;
import com.electronicsstore.store.repo.ProductRepository;
import com.electronicsstore.store.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

        List<Product> cart = user.getCart();
        logger.info("Колличество товаров в корзине у пользователя {} равно {}", user.getUsername(), cart.size());



        model.addAttribute("buttonText", "Удалить из корзины");
        model.addAttribute("formActionPrefix", "/remove-from-cart/");
        model.addAttribute("totalPrice", cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("totalCount", cart.size());


        model.addAttribute("productsInCart", cart);


        return "cart-list";
    }

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

    @GetMapping("/cart-list/delivery")
    public String orderDelivery(){
        return "delivery";
    }

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

    @GetMapping("/user-window")
    public String userWindow(Principal principal, Model model){
        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        model.addAttribute("userName", user.getUsername());
        model.addAttribute("cartItemCount", user.getCart().size());

        return "user-window";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/oplata")
    public String olataWindow(){ return "oplata"; }

    @GetMapping("/about")
    public String aboutCompany(){ return "about-company"; }

    @GetMapping("/contacts")
    public String contacts(){ return "contacts"; }

    @GetMapping("/about-delivery")
    public String aboutDelivery(){ return "about-delivery"; }

    @GetMapping("/bonus")
    public String bonus(){ return "bonus"; }

    @GetMapping("/register")
    public String Register(){ return "register"; }

}


