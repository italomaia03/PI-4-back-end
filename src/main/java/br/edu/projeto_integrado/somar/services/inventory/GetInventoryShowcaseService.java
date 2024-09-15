package br.edu.projeto_integrado.somar.services.inventory;

import br.edu.projeto_integrado.somar.dtos.inventory.GetInventoryShowcaseParams;
import br.edu.projeto_integrado.somar.dtos.inventory.GetInventoryShowcaseResponse;
import br.edu.projeto_integrado.somar.entities.Product;
import br.edu.projeto_integrado.somar.repositories.ProductRepository;
import br.edu.projeto_integrado.somar.specifications.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetInventoryShowcaseService {

    private final GetInventoryService getInventoryService;
    private final ProductRepository productRepository;

    public GetInventoryShowcaseService(GetInventoryService getInventoryService, ProductRepository productRepository) {
        this.getInventoryService = getInventoryService;
        this.productRepository = productRepository;
    }

    public List<GetInventoryShowcaseResponse> execute(String username, GetInventoryShowcaseParams params, Pageable pageable) {
        var inventory = getInventoryService.execute(username);
        Specification<Product> spec = Specification.where(ProductSpecification.hasInventory(inventory));

        if(params != null) {
            spec = buildSpecs(params, spec);
        }

        var products = productRepository.findAll(spec, pageable);
        return products.isEmpty() ? new ArrayList<>() : mapToResponse(products);
    }

    private Specification<Product> buildSpecs(GetInventoryShowcaseParams params,
                                              Specification<Product> specs) {

        if (params.getName() != null) {
            specs = specs.and(ProductSpecification.hasName(params.getName()));
        }
        if (params.getIsDamaged() != null) {
            specs = specs.and(ProductSpecification.isDamaged(params.getIsDamaged()));
        }
        if (params.getBarcode() != null) {
            specs = specs.and(ProductSpecification.hasBarcode(params.getBarcode()));
        }
        if (params.getMinAmount() != null || params.getMaxAmount() != null) {
            specs = specs.and(ProductSpecification.hasAmountBetween(params.getMinAmount(), params.getMaxAmount()));
        }
        if (params.getMinSellingPrice() != null || params.getMaxSellingPrice() != null) {
            specs = specs.and(ProductSpecification.hasSellingPriceBetween(params.getMinSellingPrice(),
                    params.getMaxSellingPrice()));
        }
        if (params.getMinBuyingPrice() != null || params.getMaxBuyingPrice() != null) {
            specs = specs.and(ProductSpecification.hasBuyingPriceBetween(params.getMinBuyingPrice(),
                    params.getMaxBuyingPrice()));
        }
        if (params.getIsExpired() != null) {
            specs = specs.and(ProductSpecification.isExpired(params.getIsExpired()));
        }

        return specs;
    }

    private List<GetInventoryShowcaseResponse> mapToResponse(Page<Product> products) {
        return products
                .stream()
                .map(data -> new GetInventoryShowcaseResponse(
                        data.getUuid(),
                        data.getName(),
                        data.getImage(),
                        data.getBatch().getAmount(),
                        data.getSellingPrice()
                ))
                .toList();
    }
}
