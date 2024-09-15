package br.edu.projeto_integrado.somar.exceptions;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException() {
        super("Product not found");
    }
}
