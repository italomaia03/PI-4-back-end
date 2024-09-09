package br.edu.projeto_integrado.somar.dtos.product.create.response;

import java.util.UUID;

public record BatchResponseDto(
        UUID uuid,
        String barcode,
        Integer amount,
        Boolean damaged
) {
}
