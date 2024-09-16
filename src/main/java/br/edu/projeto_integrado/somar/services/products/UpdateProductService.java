package br.edu.projeto_integrado.somar.services.products;

import br.edu.projeto_integrado.somar.dtos.product.update.UpdateProductRequest;
import br.edu.projeto_integrado.somar.exceptions.ProductNotFoundException;
import br.edu.projeto_integrado.somar.repositories.ProductRepository;
import br.edu.projeto_integrado.somar.services.inventory.GetInventoryService;
import br.edu.projeto_integrado.somar.specifications.ProductSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateProductService {

    private final GetInventoryService getInventoryService;
    private final ProductRepository productRepository;

    public UpdateProductService(GetInventoryService getInventoryService, ProductRepository productRepository) {
        this.getInventoryService = getInventoryService;
        this.productRepository = productRepository;
    }

    public void execute(UUID productId, UpdateProductRequest requestBody, String username) {
        var inventory = getInventoryService.execute(username);
        var spec =
                Specification
                        .where(ProductSpecification.hasInventory(inventory))
                        .and(ProductSpecification.hasUuid(productId));
        var productFound = productRepository.findOne(spec).orElseThrow(ProductNotFoundException::new);
        var batchFound = productFound.getBatch();

        batchFound.setBestBefore(requestBody.getBatch().getBestBefore());
        batchFound.setAmount(requestBody.getBatch().getAmount());
        batchFound.setBarcode(requestBody.getBatch().getBarcode());

        productFound.setName(requestBody.getName());
        productFound.setDamaged(requestBody.getIsDamaged());
        productFound.setBuyingPrice(requestBody.getBuyingPrice());
        productFound.setSellingPrice(requestBody.getSellingPrice());
        productFound.setBatch(batchFound);

        productRepository.save(productFound);
    }
}
