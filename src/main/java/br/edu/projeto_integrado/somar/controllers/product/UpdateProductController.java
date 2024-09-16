package br.edu.projeto_integrado.somar.controllers.product;

import br.edu.projeto_integrado.somar.dtos.product.update.UpdateProductRequest;
import br.edu.projeto_integrado.somar.services.products.UpdateProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/products/{productId}")
public class UpdateProductController {

    private final UpdateProductService service;

    public UpdateProductController(UpdateProductService service) {
        this.service = service;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(
            @PathVariable("productId") UUID productId,
            @Valid @RequestBody UpdateProductRequest requestBody,
            Authentication auth
    ) {
        var user = (UserDetails) auth.getPrincipal();
        service.execute(productId, requestBody, user.getUsername());
    }
}
