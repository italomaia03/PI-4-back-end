package br.edu.projeto_integrado.somar.dtos.product.create.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDto(
        UUID uuid,
        String name,
        String image,
        BigDecimal sellingPrice,
        BigDecimal buyingPrice,
        Boolean damaged
) {
}
