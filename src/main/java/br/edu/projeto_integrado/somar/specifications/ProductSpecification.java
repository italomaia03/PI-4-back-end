package br.edu.projeto_integrado.somar.specifications;

import br.edu.projeto_integrado.somar.entities.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductSpecification {

    public static Specification<Product> hasName(final String name) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(Product_.NAME)), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> hasBarcode(final String barcode) {
        return (root, query, cb) -> {
            Join<Product, Batch> batchJoin = root.join(Product_.BATCH);
            return cb.equal(batchJoin.get(Batch_.BARCODE), barcode);
        };
    }

    public static Specification<Product> hasSellingPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            } else if (minPrice != null && maxPrice != null) {
                return cb.between(root.get(Product_.SELLING_PRICE), minPrice, maxPrice);
            } else if (minPrice != null) {
                return cb.greaterThanOrEqualTo(root.get(Product_.SELLING_PRICE), minPrice);
            } else {
                return cb.lessThanOrEqualTo(root.get(Product_.SELLING_PRICE), maxPrice);
            }
        };
    }
    public static Specification<Product> hasBuyingPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            } else if (minPrice != null && maxPrice != null) {
                return cb.between(root.get(Product_.BUYING_PRICE), minPrice, maxPrice);
            } else if (minPrice != null) {
                return cb.greaterThanOrEqualTo(root.get(Product_.BUYING_PRICE), minPrice);
            } else {
                return cb.lessThanOrEqualTo(root.get(Product_.BUYING_PRICE), maxPrice);
            }
        };
    }

    public static Specification<Product> isExpired (final Boolean isExpired) {
        return (root, query, cb) -> {
            Join<Product, Batch> batchJoin = root.join(Product_.BATCH);
            return cb.equal(cb.lessThan(batchJoin.get(Batch_.BEST_BEFORE), LocalDate.now()), isExpired);
        };
    }

    public static Specification<Product> isDamaged (final Boolean isDamaged) {
        return (root, query, cb) -> cb.equal(root.get(Product_.IS_DAMAGED), isDamaged);
    }

    public static Specification<Product> hasAmountBetween(Integer minAmount, Integer maxAmount) {
        return (root, query, cb) -> {
            Join<Product, Batch> batchJoin = root.join(Product_.BATCH);
            if (minAmount == null && maxAmount == null) {
                return null;
            } else if (minAmount != null && maxAmount != null) {
                return cb.between(batchJoin.get(Batch_.AMOUNT), minAmount, maxAmount);
            } else if (minAmount != null) {
                return cb.greaterThanOrEqualTo(batchJoin.get(Batch_.AMOUNT), minAmount);
            } else {
                return cb.lessThanOrEqualTo(batchJoin.get(Batch_.AMOUNT), maxAmount);
            }
        };
    }

    public static Specification<Product> hasInventory (final Inventory inventory){
        return (root, query, cb) -> {
            Join<Product, Batch> batchJoin = root.join(Product_.BATCH);
            return cb.equal(batchJoin.get(Batch_.INVENTORY), inventory);
        };
    }
}