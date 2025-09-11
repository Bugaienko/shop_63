package ait.cohor63.shop.service;

import ait.cohor63.shop.model.entity.User;
import ait.cohor63.shop.service.interfaces.ConfirmationCodeService;
import ait.cohor63.shop.service.interfaces.EmailService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergey Bugaenko
 * {@code @date} 11.09.2025
 */

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    // import freemarker.template.Configuration;
    private final Configuration mailConfig;

    private final ConfirmationCodeService confirmationService;

    private final static String HOST = "http://localhost:8080/api";

    public EmailServiceImpl(JavaMailSender mailSender, Configuration configuration, ConfirmationCodeService confirmationService) {
        this.mailSender = mailSender;
        this.mailConfig = configuration;
        this.confirmationService = confirmationService;

        // Настройка кодировки и расположения шаблонов
        this.mailConfig.setDefaultEncoding("UTF-8");
        this.mailConfig.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/mail"));
    }

    @Override
    public void sendConfirmationEmail(User user) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Адрес отправителя. Получаем из переменных среды
            String fromAddress = System.getenv("MAIL_USERNAME");

            // Указываем отправителя
            helper.setFrom(fromAddress);

            //Указываем получателя
            helper.setTo(user.getEmail());

            // Указываем темы
            helper.setSubject("registration Confirmation");

            String emailText = generateEmailText(user);

            // Добавляем текст письма
            helper.setText(emailText, true);

            // Отправка письма
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private String generateEmailText(User user) {

        try {
            // Загрузка шаблона
            Template template = mailConfig.getTemplate("confirm_reg_mail.ftlh");

            // Генерация и сохранения кода подтверждения для пользователя
            String code = confirmationService.generateConfirmationCode(user);

            // Формировать ссылку для подтверждения регистрации
            // http://localhost:8080/api/confirm?code=значение_кода
            String confirmationLink = HOST + "/confirm?code=" + code;

            // Вставить данные пользователя (имя и ссылку)
            /* Модель -
             {
                name : value,
                confirmationLink : value
             }
             */
            Map<String, Object> model = new HashMap<>();
            model.put("name", user.getUsername());
            model.put("confirmationLink", confirmationLink);

            // Генерируем и возвращаем текст письма
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
