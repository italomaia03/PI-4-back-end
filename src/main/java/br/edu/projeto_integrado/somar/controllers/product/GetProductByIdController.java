package br.edu.projeto_integrado.somar.controllers.product;

import br.edu.projeto_integrado.somar.dtos.product.get_by_id.GetProductByIdResponse;
import br.edu.projeto_integrado.somar.services.products.GetProductByIdService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/products/{productId}")
public class GetProductByIdController {

    private final GetProductByIdService service;

    public GetProductByIdController(GetProductByIdService service) {
        this.service = service;
    }

    @GetMapping
    public GetProductByIdResponse getProductById(@PathVariable("productId") UUID productId, Authentication auth) {
        var username = (UserDetails) auth.getPrincipal();
        return service.execute(productId, username.getUsername());
    }

}
