package br.edu.projeto_integrado.somar.controllers.product;

import br.edu.projeto_integrado.somar.dtos.product.create.request.CreateProductRequestDto;
import br.edu.projeto_integrado.somar.dtos.product.create.response.CreateProductResponseDto;
import br.edu.projeto_integrado.somar.services.products.CreateProductService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
public class ProductsController {

    private final CreateProductService createProductService;

    public ProductsController(CreateProductService createProductService) {
        this.createProductService = createProductService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CreateProductResponseDto createProduct(@RequestBody CreateProductRequestDto createProductRequestDto,
                                                  Authentication authentication) {

        var username = (UserDetails) authentication.getPrincipal();
        return this.createProductService.execute(createProductRequestDto, username.getUsername());
    }
}
