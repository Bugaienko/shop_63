package ait.cohor63.shop.exception_handling;

import java.util.Objects;

/**
 * @author Sergey Bugaenko
 * {@code @date} 10.09.2025
 */

public class Response {

    private String message;

    public Response(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response: message - " + message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Response response)) return false;

        return Objects.equals(message, response.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }
}
