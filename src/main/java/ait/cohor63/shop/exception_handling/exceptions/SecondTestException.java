package ait.cohor63.shop.exception_handling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Sergey Bugaenko
 * {@code @date} 10.09.2025
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This is Second Test Exception")
public class SecondTestException extends RuntimeException {

    public SecondTestException(String message) {
        super(message);
    }
}
