package br.edu.projeto_integrado.somar.config;

import br.edu.projeto_integrado.somar.entities.Batch;
import br.edu.projeto_integrado.somar.entities.Inventory;
import br.edu.projeto_integrado.somar.entities.Product;
import br.edu.projeto_integrado.somar.repositories.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class InventoryManagement {

    private final BatchRepository batchRepository;

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
}

