package br.edu.projeto_integrado.somar.controllers.product;

import br.edu.projeto_integrado.somar.extensions.UserManagementExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(UserManagementExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GetProductByIdControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "test@test.com", password = "test@1234")
    void shouldReturnProductWhenFound() throws Exception {
        var productId = UUID.fromString("6bba5a73-34a9-452a-80d3-5f68a4b68c8f");

        mockMvc.perform(get("/api/v1/products/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + UserManagementExtension.getUserToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(productId.toString()))
                .andExpect(jsonPath("$.name").value("Product Test"))
                .andExpect(jsonPath("$.buyingPrice").value("50.0"))
                .andExpect(jsonPath("$.sellingPrice").value("100.0"))
                .andExpect(jsonPath("$.isDamaged").value(false))
                .andExpect(jsonPath("$.bestBefore").value(LocalDate.now().plusMonths(1).toString()));
    }

    @Test
    @WithMockUser(username = "test@test.com", password = "test@1234")
    void shouldReturn404WhenProductNotFound() throws Exception {
        UUID productId = UUID.randomUUID();
        mockMvc.perform(get("/api/v1/products/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + UserManagementExtension.getUserToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getProductByIdShouldThrowWhenNoTokenIsProvided() throws Exception {
        UUID productId = UUID.randomUUID();
        mockMvc.perform(get("/api/v1/products/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
