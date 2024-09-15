package br.edu.projeto_integrado.somar.services.products;

import br.edu.projeto_integrado.somar.dtos.product.get_by_id.GetProductByIdResponse;
import br.edu.projeto_integrado.somar.entities.Product;
import br.edu.projeto_integrado.somar.exceptions.ProductNotFoundException;
import br.edu.projeto_integrado.somar.repositories.ProductRepository;
import br.edu.projeto_integrado.somar.services.inventory.GetInventoryService;
import br.edu.projeto_integrado.somar.specifications.ProductSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetProductByIdService {

    private final GetInventoryService getInventoryService;
    private final ProductRepository productRepository;

    public GetProductByIdService(GetInventoryService getInventoryService, ProductRepository productRepository) {
        this.getInventoryService = getInventoryService;
        this.productRepository = productRepository;
    }

    public GetProductByIdResponse execute(UUID uuid, String username) {
        var inventory = getInventoryService.execute(username);
        var specs = Specification
                .where(ProductSpecification.hasInventory(inventory))
                .and(ProductSpecification.hasUuid(uuid));

        var productFound = productRepository
                .findOne(specs);

        var product = productFound.orElseThrow(ProductNotFoundException::new);

        return mapToResponse(product);
    }

    private GetProductByIdResponse mapToResponse(Product product) {
        return new GetProductByIdResponse(
                product.getImage(),
                product.getUuid(),
                product.getName(),
                product.getBuyingPrice(),
                product.getSellingPrice(),
                product.getDamaged(),
                product.getBatch().getBestBefore()
        );
    }
}
