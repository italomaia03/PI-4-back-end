package br.edu.projeto_integrado.somar.dtos.product.create.request;

import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link br.edu.projeto_integrado.somar.entities.Batch}
 */
public final class BatchRequestDto implements Serializable {
    @NotBlank
    private String barcode;
    @FutureOrPresent
    @NotNull
    private LocalDate bestBefore;
    @Positive
    private Integer amount;
    @NotNull
    private Boolean damaged;


    public String getBarcode() {
        return barcode;
    }

    public LocalDate getBestBefore() {
        return bestBefore;
    }

    public Integer getAmount() {
        return amount;
    }

    public Boolean getDamaged() {
        return damaged;
    }
}
