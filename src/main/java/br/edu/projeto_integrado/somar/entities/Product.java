package br.edu.projeto_integrado.somar.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLSelect;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@SQLSelect(sql = "")
public class Product extends BaseEntity {
    @Column
    private String name;
    @Column(name = "is_damaged")
    private Boolean isDamaged;
    @Column
    private String image;
    @Column(name = "selling_price")
    private BigDecimal sellingPrice;
    @Column(name = "buying_price")
    private BigDecimal buyingPrice;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "batch_id")
    private Batch batch;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDamaged() {
        return isDamaged;
    }

    public void setDamaged(Boolean damaged) {
        isDamaged = damaged;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JsonIgnore
    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", isDamaged=" + isDamaged +
                ", image='" + image + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", buyingPrice=" + buyingPrice +
                ", batch=" + batch +
                '}';
    }
}
