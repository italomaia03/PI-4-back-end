package br.edu.projeto_integrado.somar.dtos.product.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link br.edu.projeto_integrado.somar.entities.Product}
 */
public class UpdateProductRequest implements Serializable {
    @NotBlank(message = "Nome do produto não foi informado")
    private String name;
    @NotNull(message = "Informe se o produto possui avarias")
    private Boolean isDamaged;
    @PositiveOrZero(message = "Informe um valor válido acima de zero")
    private BigDecimal sellingPrice;
    @PositiveOrZero(message = "Informe um valor válido")
    private BigDecimal buyingPrice;
    private UpdateProductBatch batch;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsDamaged() {
        return isDamaged;
    }

    public void setIsDamaged(Boolean isDamaged) {
        this.isDamaged = isDamaged;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public UpdateProductBatch getBatch() {
        return batch;
    }

    public void setBatch(UpdateProductBatch batch) {
        this.batch = batch;
    }
}