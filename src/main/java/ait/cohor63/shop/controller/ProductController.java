package ait.cohor63.shop.controller;

import ait.cohor63.shop.model.dto.ProductDTO;
import ait.cohor63.shop.model.entity.Product;
import ait.cohor63.shop.service.interfaces.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Product controller", description = "Controller for operations with products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService productService) {
        this.service = productService;
    }


    @Operation(summary = "Create product", description = "Add new product", tags = { "Product" })
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ProductDTO.class)) }) })
    @PostMapping
    public ProductDTO saveProduct(@Parameter(description = "Created product object") @RequestBody ProductDTO productDTO) {
        return service.saveProduct(productDTO);
    }

    // GET /products/1
    // GET /products/176
    // GET /products/55
    @Operation(summary = "Get product by id", tags = { "Product" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)),
                            @Content(mediaType = "application/xml", schema = @Schema(implementation = ProductDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "ProductDTO not found", content = @Content) })

    @GetMapping("/{id}")
    public ProductDTO getById(@Parameter(description = "The id that needs to be fetched", required = true) @PathVariable Long id) {
        return service.getProductById(id);
    }

    // GET /products
    @GetMapping
    public List<ProductDTO> getAll() {
        return service.getAllActiveProducts();
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return service.updateProduct(id, productDTO);
    }

    // DELETE -> /products/12
    @DeleteMapping("/{productId}")
    public ProductDTO remove(@PathVariable("productId") Long id) {
        return service.deleteProductById(id);
    }

    // DELETE -> /products/by-title/?title=Banan
    @DeleteMapping("/by-title")
    public ProductDTO removeByTitle(@RequestParam String title) {
        return service.deleteProductByTitle(title);
    }

    @PutMapping("/restore/{id}")
    public ProductDTO restoreById(@PathVariable Long id) {
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
