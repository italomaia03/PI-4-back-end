package br.edu.projeto_integrado.somar.services.inventory;

import br.edu.projeto_integrado.somar.dtos.inventory.GetInventoryShowcaseParams;
import br.edu.projeto_integrado.somar.dtos.inventory.GetInventoryShowcaseResponse;
import br.edu.projeto_integrado.somar.entities.Batch;
import br.edu.projeto_integrado.somar.entities.Inventory;
import br.edu.projeto_integrado.somar.entities.Product;
import br.edu.projeto_integrado.somar.exceptions.UserNotFoundException;
import br.edu.projeto_integrado.somar.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetInventoryShowcaseServiceUnitTest {

    @Mock
    private GetInventoryService getInventoryService;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private GetInventoryShowcaseService service;
    private Inventory inventory;
    private Product productTest;
    private GetInventoryShowcaseParams params;
    private GetInventoryShowcaseResponse response;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        productTest = new Product();
        params = new GetInventoryShowcaseParams();
        var batchTest = new Batch();
        var batchUuid = UUID.randomUUID();
        var productUuid = UUID.randomUUID();
        batchTest = new Batch();
        batchTest.setId(1);
        batchTest.setUuid(batchUuid);
        productTest.setId(1);
        productTest.setUuid(productUuid);
        productTest.setName("Test Product");
        productTest.setDamaged(false);
        productTest.setImage("Test Image");
        productTest.setBuyingPrice(BigDecimal.valueOf(12.29));
        productTest.setSellingPrice(BigDecimal.valueOf(16.29));
        batchTest.setBarcode("barcode-test");
        batchTest.setAmount(10);
        batchTest.setBestBefore(LocalDate.of(2024, 1, 1));
        batchTest.setProduct(productTest);
        batchTest.setDamaged(false);
        var batches = new HashSet<Batch>();
        batches.add(batchTest);
        inventory.setBatches(batches);
        response = new GetInventoryShowcaseResponse(
                batchTest.getProduct().getUuid(),
                batchTest.getProduct().getName(),
                batchTest.getProduct().getImage(),
                batchTest.getAmount(),
                batchTest.getProduct().getSellingPrice()
        );
    }

    @Test
    public void getInventoryShowcaseShouldReturnInventoryShowcase() {
        var page = new PageImpl<>(
                List.of(productTest)
        );
        when(getInventoryService.execute(anyString())).thenReturn(inventory);
        when(productRepository.findAll(Specification.where(any()), any(Pageable.class))).thenReturn(page);

        var result = service.execute("test", params, PageRequest.of(0,1));

        verify(getInventoryService, times(1)).execute(anyString());
        verify(productRepository, times(1)).findAll(Specification.where(any()), any(Pageable.class));
        Assertions.assertEquals(result.getFirst(), response);
    }

    @Test
    public void getInventoryShowcaseShouldReturnEmptyWhenNoProductFound() {
        var page = new PageImpl<>(new ArrayList<Product>());
        when(getInventoryService.execute(anyString())).thenReturn(inventory);
        when(productRepository.findAll(Specification.where(any()), any(Pageable.class))).thenReturn(page);

        var result = service.execute("test", params, PageRequest.of(0,1));

        verify(getInventoryService, times(1)).execute(anyString());
        verify(productRepository, times(1)).findAll(Specification.where(any()), any(Pageable.class));
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void getInventoryShowcaseShouldThrowExceptionWhenInventoryNotFound() {
        when(getInventoryService.execute(anyString())).thenThrow(UserNotFoundException.class);
        Assertions.assertThrows(UserNotFoundException.class, () -> service.execute("test", params, PageRequest.of(0,1)));
    }
}
