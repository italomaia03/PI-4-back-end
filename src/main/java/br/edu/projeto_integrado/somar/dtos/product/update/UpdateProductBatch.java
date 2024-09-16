package br.edu.projeto_integrado.somar.dtos.product.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link br.edu.projeto_integrado.somar.entities.Batch}
 */
public class UpdateProductBatch implements Serializable {
    @NotBlank(message = "Informe o código de barras do produto")
    private String barcode;
    @NotNull(message = "Informe a data de validade do produto")
    private LocalDate bestBefore;
    @PositiveOrZero(message = "Informe um número válido")
    private Integer amount;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public LocalDate getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(LocalDate bestBefore) {
        this.bestBefore = bestBefore;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}