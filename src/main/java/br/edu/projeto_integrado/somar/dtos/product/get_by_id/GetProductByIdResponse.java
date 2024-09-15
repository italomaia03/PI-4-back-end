package br.edu.projeto_integrado.somar.dtos.product.get_by_id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record GetProductByIdResponse (
        String image,
        UUID uuid,
        String name,
        BigDecimal buyingPrice,
        BigDecimal sellingPrice,
        Boolean isDamaged,
        LocalDate bestBefore
) {
}
