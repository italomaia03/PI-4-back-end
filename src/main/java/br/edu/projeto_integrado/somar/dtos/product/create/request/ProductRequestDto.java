package br.edu.projeto_integrado.somar.dtos.product.create.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link br.edu.projeto_integrado.somar.entities.Product}
 */
public final class ProductRequestDto implements Serializable {

    @NotBlank
    private String name;
    @NotNull
    private Boolean damaged;
    @URL
    private String image;
    @PositiveOrZero
    @NotNull
    private BigDecimal sellingPrice;
    @PositiveOrZero
    @NotNull
    private BigDecimal buyingPrice;

    public String getName() {
        return name;
    }

    public Boolean getDamaged() {
        return damaged;
    }

    public String getImage() {
        return image;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

}

