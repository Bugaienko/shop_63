package ait.cohor63.shop.controller;

import ait.cohor63.shop.model.entity.Product;
import ait.cohor63.shop.service.interfaces.ProductService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Sergey Bugaenko
 * {@code @date} 29.08.2025
 */

// http://localhost:8080/api/products

@RestController
@RequestMapping("/products") // Указывает, что контроллер обрабатывает запросы, связанные с ресурсом products
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService productService) {
        this.service = productService;
    }


    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        return service.saveProduct(product);
    }

    // GET /products/1
    // GET /products/176
    // GET /products/55
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    // GET /products
    @GetMapping
    public List<Product> getAll() {
        return service.getAllActiveProducts();
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return service.updateProduct(id, product);
    }

    // DELETE -> /products/12
    @DeleteMapping("/{productId}")
    public Product remove(@PathVariable("productId") Long id) {
        return service.deleteProductById(id);
    }

    // DELETE -> /products/by-title/?title=Banan
    @DeleteMapping("/by-title")
    public Product removeByTitle(@RequestParam String title) {
        return service.deleteProductByTitle(title);
    }

    @PutMapping("/{id}")
    public Product restoreById(@PathVariable Long id) {
        return service.restoreProductById(id);
    }

    @GetMapping("/count")
    public long getProductCount() {
        return service.getProductCount();
    }

    @GetMapping("/total-price")
    public BigDecimal getTotalPrice() {
        return service.getTotalPrice();
    }

    @GetMapping("/average-price")
    public BigDecimal getAveragePrice() {
        return service.getAveragePrice();
    }


    // /products?title=Banana
//    @DeleteMapping
//    public Product removeUniversal(@RequestParam(required = false) Long id, @RequestParam(required = false) String title) {
//        if (id != null) {
//            return service.deleteProductById(id);
//        } if (title != null) {
//            return service.deleteProductByTitle(title);
//        }
//        return null;
//    }



}

// POST /products - POST - определяет действие (создание нового), /products - определяет ресурс с которым совершает действие
// GET /products/2

// PUT /products/4
// DELETE /products/1

// /order/12/cancel
