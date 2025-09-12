package ait.cohor63.shop.service.interfaces;

import ait.cohor63.shop.model.dto.UserRegisterDTO;

/**
 * @author Sergey Bugaenko
 * {@code @date} 12.09.2025
 */

public interface UserService {
    void registerUser(UserRegisterDTO userRegisterDTO);
}
