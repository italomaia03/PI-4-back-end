package br.edu.projeto_integrado.somar.services.products;

import br.edu.projeto_integrado.somar.dtos.product.create.request.BatchRequestDto;
import br.edu.projeto_integrado.somar.dtos.product.create.request.ProductRequestDto;
import br.edu.projeto_integrado.somar.dtos.product.create.request.CreateProductRequestDto;
import br.edu.projeto_integrado.somar.dtos.product.create.response.BatchResponseDto;
import br.edu.projeto_integrado.somar.dtos.product.create.response.CreateProductResponseDto;
import br.edu.projeto_integrado.somar.dtos.product.create.response.ProductResponseDto;
import br.edu.projeto_integrado.somar.entities.Batch;
import br.edu.projeto_integrado.somar.entities.Inventory;
import br.edu.projeto_integrado.somar.entities.Product;
import br.edu.projeto_integrado.somar.exceptions.UserNotFoundException;
import br.edu.projeto_integrado.somar.repositories.BatchRepository;
import br.edu.projeto_integrado.somar.repositories.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class CreateProductService {

    private final BatchRepository batchRepository;
    private final UserRepository userRepository;

    public CreateProductService(BatchRepository batchRepository, UserRepository userRepository) {
        this.batchRepository = batchRepository;
        this.userRepository = userRepository;
    }

    public CreateProductResponseDto execute(CreateProductRequestDto dto, String username) {
        var user = this.userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);

        var inventory = user.getInventory();
        var batch = this.batchRepository
                .findByBarcode(dto.getBatch().getBarcode());
        var product = mapToProduct(dto.getProduct());
        if (batch.isPresent()) {
            var amount = dto.getBatch().getAmount();
            batch.get().setAmount(batch.get().getAmount() + amount);
            return mapToResponse(batch.get().setProduct(product));
        }

        return mapToResponse(createBatch(dto.getBatch(), product, inventory));
    }

    private Batch createBatch(BatchRequestDto dto, Product product, Inventory inventory) {
        var batch = mapToBatch(dto);
        batch.setProduct(product);
        batch.setInventory(inventory);
        return this.batchRepository.save(batch);
    }

    private Product mapToProduct(ProductRequestDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDamaged(productDto.getDamaged());
        product.setBuyingPrice(productDto.getBuyingPrice());
        product.setSellingPrice(productDto.getSellingPrice());
        if (productDto.getImage() != null) {
            product.setImage(productDto.getImage());
        }
        return product;
    }

    private Batch mapToBatch(BatchRequestDto dto) {
        Batch batch = new Batch();
        batch.setBarcode(dto.getBarcode());
        batch.setBestBefore(dto.getBestBefore());
        batch.setAmount(dto.getAmount());
        batch.setDamaged(dto.getDamaged());
        return batch;
    }

    private CreateProductResponseDto mapToResponse (Batch batch) {
        var productDto = new ProductResponseDto(
                batch.getProduct().getUuid(),
                batch.getProduct().getName(),
                batch.getProduct().getImage(),
                batch.getProduct().getSellingPrice(),
                batch.getProduct().getBuyingPrice(),
                batch.getProduct().getDamaged()
        );
        var batchDto = new BatchResponseDto(
                batch.getUuid(),
                batch.getBarcode(),
                batch.getAmount(),
                batch.getDamaged()
        );
        return new CreateProductResponseDto(
                batchDto,
                productDto
        );
    }
}
