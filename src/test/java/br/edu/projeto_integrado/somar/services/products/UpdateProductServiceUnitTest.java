package br.edu.projeto_integrado.somar.services.products;

import br.edu.projeto_integrado.somar.dtos.product.update.UpdateProductBatch;
import br.edu.projeto_integrado.somar.dtos.product.update.UpdateProductRequest;
import br.edu.projeto_integrado.somar.entities.Batch;
import br.edu.projeto_integrado.somar.entities.Inventory;
import br.edu.projeto_integrado.somar.entities.Product;
import br.edu.projeto_integrado.somar.exceptions.ProductNotFoundException;
import br.edu.projeto_integrado.somar.exceptions.UserNotFoundException;
import br.edu.projeto_integrado.somar.repositories.ProductRepository;
import br.edu.projeto_integrado.somar.services.inventory.GetInventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateProductServiceUnitTest {

    @Mock
    private GetInventoryService getInventoryService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UpdateProductService service;

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(getInventoryService.execute(any(String.class))).thenReturn(new Inventory());
        when(productRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> service.execute(UUID.randomUUID(), any(UpdateProductRequest.class),
                "test"));
    }

    @Test
    void shouldThrowExceptionWhenInventoryNotFound() {
        when(getInventoryService.execute(any(String.class))).thenThrow(new UserNotFoundException());

        assertThrows(UserNotFoundException.class, () -> service.execute(UUID.randomUUID(),
                any(UpdateProductRequest.class), "test"));
    }

    @Test
    void shouldNotThrowExceptionWhenUpdateProductSuccessful() {
        var productFound = new Product();
        var batchFound = new Batch();
        productFound.setBatch(batchFound);
        when(getInventoryService.execute(any(String.class))).thenReturn(new Inventory());
        when(productRepository.findOne(any(Specification.class))).thenReturn(Optional.of(productFound));
        var request = new UpdateProductRequest();
        var batch = new UpdateProductBatch();
        batch.setBarcode("test-barcode");
        batch.setBestBefore(LocalDate.now());
        batch.setAmount(2);
        request.setName("test");
        request.setBuyingPrice(new BigDecimal("100.00"));
        request.setSellingPrice(new BigDecimal("150.00"));
        request.setIsDamaged(false);
        request.setBatch(batch);

        assertDoesNotThrow(() -> service.execute(UUID.randomUUID(), request, "test"));
    }
}
