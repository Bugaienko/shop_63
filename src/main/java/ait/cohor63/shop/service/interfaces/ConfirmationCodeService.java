package ait.cohor63.shop.service.interfaces;

import ait.cohor63.shop.model.entity.User;

/**
 * @author Sergey Bugaenko
 * {@code @date} 11.09.2025
 */

public interface ConfirmationCodeService {

    // Метод для генерации кода подтверждения
    String generateConfirmationCode(User user);
}
