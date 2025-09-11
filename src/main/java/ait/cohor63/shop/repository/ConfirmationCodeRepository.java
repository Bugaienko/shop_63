package ait.cohor63.shop.repository;

import ait.cohor63.shop.model.entity.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Sergey Bugaenko
 * {@code @date} 11.09.2025
 */

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {

    // Метод для поиска кода по его полю code (значение)
    Optional<ConfirmationCode> findByCode(String code);
}
