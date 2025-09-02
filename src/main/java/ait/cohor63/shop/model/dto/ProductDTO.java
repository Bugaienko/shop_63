package ait.cohor63.shop.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

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
    private String title;

    @Schema(description = "Product price", example = "8.50")
    private BigDecimal price;


    @Override
    public String toString() {
        return String.format("Product: id - %d, title - %s, price - %s",
                id, title, price);
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof ProductDTO that)) return false;

        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(price);
        return result;
    }

    public ProductDTO() {
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



}
