package ait.cohor63.shop.exception_handling;

import java.util.List;
import java.util.Objects;

/**
 * @author Sergey Bugaenko
 * {@code @date} 10.09.2025
 */

public class ValidationResponse {
    private List<String> errors;

    public ValidationResponse(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "Response: errors - " + errors;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof ValidationResponse that)) return false;

        return Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(errors);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
