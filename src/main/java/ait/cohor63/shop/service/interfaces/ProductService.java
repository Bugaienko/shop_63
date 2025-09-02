package ait.cohor63.shop.service.interfaces;

import ait.cohor63.shop.model.dto.ProductDTO;
import ait.cohor63.shop.model.entity.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Sergey Bugaenko
 * {@code @date} 29.08.2025
 */

public interface ProductService {

    ProductDTO saveProduct(ProductDTO productDTO);

    List<ProductDTO> getAllActiveProducts();
    ProductDTO getProductById(Long id);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    ProductDTO deleteProductById(Long id);

    ProductDTO deleteProductByTitle(String title);
    ProductDTO restoreProductById(Long id);

    long getProductCount();

    BigDecimal getTotalPrice();

    BigDecimal getAveragePrice();


}
