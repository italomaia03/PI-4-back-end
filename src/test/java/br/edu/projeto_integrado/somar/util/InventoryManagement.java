package br.edu.projeto_integrado.somar.util;

import br.edu.projeto_integrado.somar.entities.Batch;
import br.edu.projeto_integrado.somar.entities.Inventory;
import br.edu.projeto_integrado.somar.entities.Product;
import br.edu.projeto_integrado.somar.repositories.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class InventoryManagement {

    private final BatchRepository batchRepository;
    private UUID productUuid;

    @Autowired
    public InventoryManagement(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    public void createBatchesAndProducts(Inventory inventory) {
        for (int i = 1; i <= 5; i++) {
            Batch batch = new Batch();
            batch.setBarcode("BARCODE" + i);
            batch.setBestBefore(LocalDate.now().plusMonths(i));
            batch.setAmount(10 * i);
            batch.setDamaged(false);
            batch.setInventory(inventory);

            Product product = new Product();
            product.setName("Product " + i);
            product.setDamaged(false);
            product.setImage("image" + i + ".png");
            product.setSellingPrice(BigDecimal.valueOf(100 + (i * 10)));
            product.setBuyingPrice(BigDecimal.valueOf(50 + (i * 5)));
            product.setBatch(batch);

            batch.setProduct(product);

            batchRepository.save(batch);
        }
    }

    public void createKnownProduct(Inventory inventory) {
        Batch batch = new Batch();
        batch.setBarcode("BARCODE");
        batch.setBestBefore(LocalDate.now().plusMonths(1));
        batch.setAmount(100);
        batch.setDamaged(false);
        batch.setInventory(inventory);

        productUuid = UUID.fromString("6bba5a73-34a9-452a-80d3-5f68a4b68c8f");
        Product product = new Product();
        product.setUuid(productUuid);
        product.setName("Product Test");
        product.setDamaged(false);
        product.setImage("image.png");
        product.setSellingPrice(BigDecimal.valueOf(100));
        product.setBuyingPrice(BigDecimal.valueOf(50));
        product.setBatch(batch);

        batch.setProduct(product);

        batchRepository.save(batch);
    }

    public UUID getProductUuid() {
        return productUuid;
    }
}

