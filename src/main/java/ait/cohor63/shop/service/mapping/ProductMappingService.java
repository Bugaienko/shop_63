package ait.cohor63.shop.service.mapping;

import ait.cohor63.shop.model.dto.ProductDTO;
import ait.cohor63.shop.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Bugaenko
 * {@code @date} 02.09.2025
 */

@Mapper(componentModel = "spring")
public interface ProductMappingService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    Product mapDtoToEntity(ProductDTO dto);

    ProductDTO mapEntityToDto(Product entity);

//    public Product mapDtoToEntity(ProductDTO dto) {
//        Product entity = new Product();
//        entity.setTitle(dto.getTitle());
//        entity.setPrice(dto.getPrice());
//        return entity;
//    }
//
//    public ProductDTO mapEntityToDto(Product entity) {
//        ProductDTO dto = new ProductDTO();
//        dto.setId(entity.getId());
//        dto.setPrice(entity.getPrice());
//        dto.setTitle(entity.getTitle());
//
//        return dto;
//    }
}
