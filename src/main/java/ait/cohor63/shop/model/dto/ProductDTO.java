package ait.cohor63.shop.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Sergey Bugaenko
 * {@code @date} 29.08.2025
 */
@Schema(description = "DTP for Product")
public class ProductDTO {

    @Schema(description = "ProductDTO unique identifier", example = "777", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Product title", example = "Banana")
    @NotNull(message = "Product title cannot be null") // пропустит ""
//    @NotEmpty проверяет, что поле не null и что оно не пустое (может состоять только из пробелов) пропустит "  "
    @NotBlank(message = "Product title cannot be empty")
    // Apple, Apple green
//    @Pattern(regexp = "^[A-Z][a-z ]{2,}$", message = "")
//    @Pattern(regexp = "^[A-Z][a-zA-Z0-9 ]{2,}$", message = "")
    // Первая большая, вторая только маленькая, третья (обязательная) и последующие большая или маленькая буква лат.алфавита, цифра или пробел
    @Pattern(regexp = "^[A-Z][a-z][a-zA-Z0-9 ]+$", message = "Product title should br at least 3 character, start with a capital letter, and contains only letters, digits and spaces")
    private String title;

    @Schema(description = "Product price", example = "8.50")
    @DecimalMin(value = "1.0", message = "Product price should be greater or equal than 1.0")
    @DecimalMax(value = "100000", inclusive = false, message = "Product price should be less than 100_000")
    private BigDecimal price;

    private String image;


    @Override
    public String toString() {
        return String.format("Product: id - %d, title - %s, price - %s, image - %s",
                id, title, price, image);
    }

    public ProductDTO() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof ProductDTO that)) return false;

        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(price, that.price) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(price);
        result = 31 * result + Objects.hashCode(image);
        return result;
    }
}
