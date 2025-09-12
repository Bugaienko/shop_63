package ait.cohor63.shop.service.mapping;

import ait.cohor63.shop.model.dto.UserRegisterDTO;
import ait.cohor63.shop.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Sergey Bugaenko
 * {@code @date} 12.09.2025
 */

@Mapper(componentModel = "spring")
public interface UserMapperService {

    @Mapping(target = "id", ignore = true)
    User mapDtoToEntity(UserRegisterDTO dto);
}
