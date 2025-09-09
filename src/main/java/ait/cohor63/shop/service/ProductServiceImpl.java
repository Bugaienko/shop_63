package ait.cohor63.shop.service;

import ait.cohor63.shop.model.dto.ProductDTO;
import ait.cohor63.shop.model.entity.Product;
import ait.cohor63.shop.repository.ProductRepository;
import ait.cohor63.shop.service.interfaces.ProductService;
import ait.cohor63.shop.service.mapping.ProductMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Bugaenko
 * {@code @date} 29.08.2025
 */


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final ProductMappingService mapper;

    // SLF4J
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);


    public ProductServiceImpl(ProductRepository repository, ProductMappingService mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = mapper.mapDtoToEntity(productDTO);
//        product.setActive(true);
        return mapper.mapEntityToDto(repository.save(product));
    }

    @Override
    public List<ProductDTO> getAllActiveProducts() {
//        List<Product> result = new ArrayList<>();
//        List<Product> list = repository.findAll();
//        for (Product product : list) {
//            if (product.isActive()) result.add(product);
//        }
//        return result;
        return repository.findAll().stream()
                .filter(Product::isActive)
                // Маппинг каждый продукт в DTO
                .map(mapper::mapEntityToDto)
                .toList();
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = repository.findById(id).orElse(null);
        if (product == null || !product.isActive()) {
            return null;
        }
        return mapper.mapEntityToDto(product);
    }

//    @Override
//    public ProductDTO getProductById(Long id) {
//        logger.info("Method getProductById called with parameter: {}", id);
//        logger.warn("Method getProductById called with parameter: {}", id);
//        logger.error("Method getProductById called with parameter: {}", id);
//
//        Product product = repository.findById(id).orElse(null);
//
//        // null ->
//        // true || ? -> true
//        // Product
//        // false || ? ->
//
//        if (product == null || !product.isActive()) {
//            return null;
//        }
//
//        return mapper.mapEntityToDto(product);
//
//        // false && ? -> false
//        // false & ? (будет посчитано) -> false
//    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        return null;
    }

    @Override
    public ProductDTO deleteProductById(Long id) {
        return null;
    }

    @Override
    public ProductDTO deleteProductByTitle(String title) {
        return null;
    }

    @Override
    public ProductDTO restoreProductById(Long id) {
        return null;
    }

    @Override
    public long getProductCount() {
        return 0;
    }

    @Override
    public BigDecimal getTotalPrice() {
        return null;
    }

    @Override
    public BigDecimal getAveragePrice() {
        return null;
    }
}
