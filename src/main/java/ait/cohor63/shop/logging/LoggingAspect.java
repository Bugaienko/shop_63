package ait.cohor63.shop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Sergey Bugaenko
 * {@code @date} 09.09.2025
 */


@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* ait.cohor63.shop.service.ProductServiceImpl.saveProduct(..))")
    public void saveProduct() {
        // Метод без тела, используется только для задания PointCut
    }

//    @Before("saveProduct()")
    public void beforeSavingProduct() {
        logger.info("Method `saveProduct` in class ProductServiceImpl was called");
    }

    // Advice - дополнительная логика, которую мы добавляем к основной бизнес-логике приложения
    @Before("saveProduct()")
    public void beforeSavingProductWithArgs(JoinPoint joinPoint) {

        // Извлекаем параметры метода - Object[] - массив параметров вызова перехваченного метода
        Object[] params = joinPoint.getArgs();

        // Логируем информацию о вызове метода и его параметре
        logger.info("Метод saveProduct вызван с параметром {}", params[0]);
    }



}
