package ait.cohor63.shop.service.mapping;

import ait.cohor63.shop.model.dto.CustomerDTO;
import ait.cohor63.shop.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
* @author Sergey Bugaenko
* {@code @date} 15.09.2025
*/

@Mapper(componentModel = "spring", uses = CartMappingService.class)
public interface CustomerMapperService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    Customer mapDtoToEntity(CustomerDTO dto);

    CustomerDTO mapEntityToDto(Customer entity);
}
