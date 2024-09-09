package br.edu.projeto_integrado.somar.dtos.product.create.response;

public record CreateProductResponseDto(
        BatchResponseDto batch,
        ProductResponseDto product
) {
}
