package br.edu.projeto_integrado.somar.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "batches")
public class Batch extends BaseEntity {
    @Column(nullable = false)
    private String barcode;
    @Column(name = "is_damaged", nullable = false)
    private Boolean isDamaged;
    @Column(name = "best_before", nullable = false)
    private LocalDate bestBefore;
    @Column
    private Integer amount;

    @OneToOne(
            mappedBy = "batch",
            cascade = CascadeType.ALL
    )
    private Product product;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Boolean getDamaged() {
        return isDamaged;
    }

    public void setDamaged(Boolean damaged) {
        isDamaged = damaged;
    }

    public LocalDate getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(LocalDate bestBefore) {
        this.bestBefore = bestBefore;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public Batch setProduct(Product product) {
        if (product == null) {
            if (this.product != null) {
                this.product.setBatch(null);
            }
        } else {
            product.setBatch(this);
        }

        this.product = product;
        return this;
    }

    @JsonIgnore
    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
