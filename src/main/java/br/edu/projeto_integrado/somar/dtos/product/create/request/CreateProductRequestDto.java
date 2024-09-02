package br.edu.projeto_integrado.somar.dtos.product.create.request;

import jakarta.validation.constraints.*;

import java.io.Serializable;

public final class CreateProductRequestDto implements Serializable {

    private BatchRequestDto batch;

    private ProductRequestDto product;

    @NotBlank
    @Positive
    private Integer amount;

    public BatchRequestDto getBatch() {
        return batch;
    }

    public ProductRequestDto getProduct() {
        return product;
    }

}