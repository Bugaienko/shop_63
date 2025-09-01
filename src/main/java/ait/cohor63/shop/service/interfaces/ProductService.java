package ait.cohor63.shop.service.interfaces;

import ait.cohor63.shop.model.entity.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Sergey Bugaenko
 * {@code @date} 29.08.2025
 */

public interface ProductService {

    Product saveProduct(Product product);

    List<Product> getAllActiveProducts();
    Product getProductById(Long id);

    Product updateProduct(Long id, Product product);

    Product deleteProductById(Long id);

    Product deleteProductByTitle(String title);
    Product restoreProductById(Long id);

    long getProductCount();

    BigDecimal getTotalPrice();

    BigDecimal getAveragePrice();


}
