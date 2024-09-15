package br.edu.projeto_integrado.somar.dtos.inventory;

public enum GetProductSort {
    NAME("name"),
    SELLING_PRICE("sellingPrice");

    private final String description;
    GetProductSort(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
