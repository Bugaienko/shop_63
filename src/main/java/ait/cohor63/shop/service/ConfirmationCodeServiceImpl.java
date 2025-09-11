package ait.cohor63.shop.service;

import ait.cohor63.shop.model.entity.ConfirmationCode;
import ait.cohor63.shop.model.entity.User;
import ait.cohor63.shop.repository.ConfirmationCodeRepository;
import ait.cohor63.shop.service.interfaces.ConfirmationCodeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Sergey Bugaenko
 * {@code @date} 11.09.2025
 */

@Service
public class ConfirmationCodeServiceImpl implements ConfirmationCodeService {

    private final ConfirmationCodeRepository repository;

    public ConfirmationCodeServiceImpl(ConfirmationCodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public String generateConfirmationCode(User user) {

        // Генерация уникального кода
        String code = UUID.randomUUID().toString();

        // создаю объект ConfirmationCode и сохраняю его в базу
        ConfirmationCode confirmationCode = new ConfirmationCode();
        confirmationCode.setCode(code);
        confirmationCode.setUser(user);
        confirmationCode.setExpired(LocalDateTime.now().plusDays(1)); // Устанавливаем срок действия кода 1 день
//        confirmationCode.setExpired(LocalDateTime.now().plusMinutes(2)); // Устанавливаем срок действия кода 2 минуты

        repository.save(confirmationCode); // Сохраняем код в БД

        return code; // Возвращаем сгенерированный код
    }
}
