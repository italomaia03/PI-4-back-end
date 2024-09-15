package br.edu.projeto_integrado.somar.dtos.inventory;

import java.math.BigDecimal;
import java.util.UUID;

public record GetInventoryShowcaseResponse(
        UUID uuid,
        String name,
        String image,
        Integer quantity,
        BigDecimal sellingPrice
) {
}
