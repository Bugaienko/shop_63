package ait.cohor63.shop.service.mapping;

import ait.cohor63.shop.model.dto.CartDTO;
import ait.cohor63.shop.model.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Sergey Bugaenko
 * {@code @date} 15.09.2025
 */

@Mapper(componentModel = "spring")
public interface CartMappingService {

    // тут НЕТ uses = CustomerMapperService.class

    // Чтобы не тащить обратно Customer и не зациклиться:
    @Mapping(target = "customer", ignore = true)
    Cart mDtoToEntity(CartDTO dto);

    // и в DTO тоже не уводим вглубь
    @Mapping(target = "customer", ignore = true)
    CartDTO mDtoToEntity(Cart entity);
}
