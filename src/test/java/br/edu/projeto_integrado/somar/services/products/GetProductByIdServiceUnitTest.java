package br.edu.projeto_integrado.somar.services.products;

import br.edu.projeto_integrado.somar.dtos.product.get_by_id.GetProductByIdResponse;
import br.edu.projeto_integrado.somar.entities.Batch;
import br.edu.projeto_integrado.somar.entities.Inventory;
import br.edu.projeto_integrado.somar.entities.Product;
import br.edu.projeto_integrado.somar.exceptions.ProductNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProductByIdServiceUnitTest {

    @Mock
    private GetInventoryService getInventoryService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductByIdService service;

    @Test
    void shouldReturnProductResponseWhenProductExists() {
        UUID productId = UUID.randomUUID();
        String username = "testuser";
        Inventory inventory = new Inventory();
        Product product = new Product();
        Batch batch = new Batch();
        batch.setBestBefore(LocalDate.now().plusDays(30L));
        product.setUuid(productId);
        product.setName("Product Name");
        product.setBuyingPrice(new BigDecimal("10.00"));
        product.setSellingPrice(new BigDecimal("15.00"));
        product.setDamaged(false);
        product.setImage("image.jpg");
        product.setBatch(batch);

        when(getInventoryService.execute(username)).thenReturn(inventory);
        when(productRepository.findOne(any(Specification.class))).thenReturn(Optional.of(product));

        GetProductByIdResponse response = service.execute(productId, username);

        assertEquals(product.getUuid(), response.uuid());
        assertEquals(product.getName(), response.name());
        assertEquals(product.getBuyingPrice(), response.buyingPrice());
        assertEquals(product.getSellingPrice(), response.sellingPrice());
        assertEquals(product.getDamaged(), response.isDamaged());
        assertEquals(product.getBatch().getBestBefore(), response.bestBefore());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        UUID productId = UUID.randomUUID();
        String username = "testuser";
        Inventory inventory = new Inventory();

        when(getInventoryService.execute(username)).thenReturn(inventory);
        when(productRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> service.execute(productId, username));
    }
}
