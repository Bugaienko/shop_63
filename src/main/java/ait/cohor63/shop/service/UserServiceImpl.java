package ait.cohor63.shop.service;

import ait.cohor63.shop.model.dto.UserRegisterDTO;
import ait.cohor63.shop.model.entity.ConfirmationCode;
import ait.cohor63.shop.model.entity.User;
import ait.cohor63.shop.repository.ConfirmationCodeRepository;
import ait.cohor63.shop.repository.UserRepository;
import ait.cohor63.shop.service.interfaces.EmailService;
import ait.cohor63.shop.service.interfaces.RoleService;
import ait.cohor63.shop.service.interfaces.UserService;
import ait.cohor63.shop.service.mapping.UserMapperService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

/**
 * @author Sergey Bugaenko
 * {@code @date} 12.09.2025
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;
    private final EmailService  emailService;
    private final UserMapperService mapper;

    private final ConfirmationCodeRepository confirmationCodeRepository;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder encoder, EmailService emailService, UserMapperService mapper, ConfirmationCodeRepository confirmationCodeRepository) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.encoder = encoder;
        this.emailService = emailService;
        this.mapper = mapper;
        this.confirmationCodeRepository = confirmationCodeRepository;
    }

    @Override
    @Transactional
    public void registerUser(UserRegisterDTO userRegisterDTO) {

        User user = mapper.mapDtoToEntity(userRegisterDTO);

        Optional<User> optionalUser = userRepository.findUserByEmail(user.getEmail());

        // Проверка существования пользователя с такими email
        if (userRepository.existsByEmail(user.getEmail()) && optionalUser.get().isActive()) {
            throw new RuntimeException("Email is already in use");
        }

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            /*
            Если мы не хотим менять пароль существующему пользователю
            user.setUsername(userRegisterDTO.username()); // опция
            emailService.sendConfirmationEmail(user);
            return;
             */
        } else {
            user.setRoles(Set.of(roleService.getRoleUser()));
        }

        // Два сценария работы с паролями - предусматриваем, что
        user.setPassword(encoder.encode(userRegisterDTO.password()));

        user.setActive(false);

        userRepository.save(user);

        emailService.sendConfirmationEmail(user);
    }


//    @Transactional
    @Override
    public String confirmEmail(String code) {
        ConfirmationCode confirmation = confirmationCodeRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Confirmation code not found"));

        // проверяем, что срок действия кода НЕ истек.
        if (confirmation.getExpired().isAfter(LocalDateTime.now())) {

            User user = confirmation.getUser();
            user.setActive(true);
            userRepository.save(user);
            return  user.getEmail() + " confirmed!";
        }

        throw new RuntimeException("Wrong confirmation code");
    }


}
