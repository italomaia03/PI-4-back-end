package br.edu.projeto_integrado.somar.controllers.inventory;

import br.edu.projeto_integrado.somar.dtos.inventory.GetInventoryShowcaseParams;
import br.edu.projeto_integrado.somar.dtos.inventory.GetInventoryShowcaseResponse;
import br.edu.projeto_integrado.somar.dtos.inventory.GetProductSort;
import br.edu.projeto_integrado.somar.services.inventory.GetInventoryShowcaseService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final GetInventoryShowcaseService showcaseService;

    public InventoryController(GetInventoryShowcaseService showcaseService) {
        this.showcaseService = showcaseService;
    }

    @GetMapping("/showcase")
    public List<GetInventoryShowcaseResponse> showcase(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String barcode,
            @RequestParam(required = false) Integer minAmount,
            @RequestParam(required = false) Integer maxAmount,
            @RequestParam(required = false) BigDecimal minSellingPrice,
            @RequestParam(required = false) BigDecimal maxSellingPrice,
            @RequestParam(required = false) BigDecimal minBuyingPrice,
            @RequestParam(required = false) BigDecimal maxBuyingPrice,
            @RequestParam(required = false) Boolean isExpired,
            @RequestParam(required = false) Boolean isDamaged,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false) GetProductSort sortBy,
            @RequestParam(required = false) Sort.Direction direction,
            Authentication authentication
    ) {
        var username = (UserDetails) authentication.getPrincipal();
        var params = mapParams(name, barcode, minAmount, maxAmount, minSellingPrice, maxSellingPrice, minBuyingPrice, maxBuyingPrice, isExpired, isDamaged);
        var pageable = mapPageable(page, limit, sortBy, direction);
        return this.showcaseService.execute(username.getUsername(), params, pageable);
    }

    private static PageRequest mapPageable(Integer page, Integer limit, GetProductSort sortBy, Sort.Direction direction) {
        if (sortBy != null && direction != null) {
            var sort = Sort.by(direction, sortBy.getDescription());
            return PageRequest.of(page, limit, sort);
        } else if (sortBy != null) {
            var sort = Sort.by(sortBy.getDescription());
            return PageRequest.of(page, limit, sort);
        } else if (direction != null) {
            return PageRequest.of(page, limit, direction);
        }
        return PageRequest.of(page - 1, limit);
    }

    private GetInventoryShowcaseParams mapParams(String name, String barcode, Integer minAmount, Integer maxAmount, BigDecimal minSellingPrice, BigDecimal maxSellingPrice, BigDecimal minBuyingPrice, BigDecimal maxBuyingPrice, Boolean isExpired, Boolean isDamaged) {
        var params = new GetInventoryShowcaseParams();
        params.setName(name);
        params.setBarcode(barcode);
        params.setMinAmount(minAmount);
        params.setMaxAmount(maxAmount);
        params.setMinSellingPrice(minSellingPrice);
        params.setMaxSellingPrice(maxSellingPrice);
        params.setMinBuyingPrice(minBuyingPrice);
        params.setMaxBuyingPrice(maxBuyingPrice);
        params.setExpired(isExpired);
        params.setDamaged(isDamaged);
        return params;
    }
}
