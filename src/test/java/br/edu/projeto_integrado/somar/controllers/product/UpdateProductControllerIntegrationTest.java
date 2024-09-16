package br.edu.projeto_integrado.somar.controllers.product;

import br.edu.projeto_integrado.somar.dtos.product.update.UpdateProductBatch;
import br.edu.projeto_integrado.somar.dtos.product.update.UpdateProductRequest;
import br.edu.projeto_integrado.somar.extensions.UserManagementExtension;
import br.edu.projeto_integrado.somar.util.InventoryManagement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(UserManagementExtension.class)
public class UpdateProductControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;
    private UpdateProductRequest request;
    @Autowired
    private InventoryManagement inventoryManagement;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() throws Exception {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        request = new UpdateProductRequest();
        var batch = new UpdateProductBatch();
        batch.setBarcode("test-barcode-update");
        batch.setAmount(100);
        batch.setBestBefore(LocalDate.now().plusMonths(1L));
        request.setName("test product update");
        request.setBuyingPrice(new BigDecimal("150.00"));
        request.setSellingPrice(new BigDecimal("150.00"));
        request.setIsDamaged(false);
        request.setBatch(batch);
    }

    @Test
    @WithMockUser(username = "test@test.com", password = "test@1234")
    public void updateProductShouldFailIfNoBodyIsPassed() throws Exception {
        mvc.perform(
                        put("/api/v1/products/{productId}", inventoryManagement.getProductUuid())
                                .header("Authorization", UserManagementExtension.getUserToken())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test@test.com", password = "test@1234")
    public void updateProductShouldFailIfBodyIsInvalid() throws Exception {
        request.setName(null);
        String requestBody = mapper.writeValueAsString(this.request);
        mvc.perform(
                        put("/api/v1/products/{productId}", inventoryManagement.getProductUuid())
                                .header("Authorization", UserManagementExtension.getUserToken())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateProductShouldFailIfNoTokenIsPassed() throws Exception {
        String requestBody = mapper.writeValueAsString(this.request);
        mvc.perform(
                        put("/api/v1/products/{productId}", inventoryManagement.getProductUuid())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void updateProductShouldFailIfUserTokenIsInvalid() throws Exception {
        String requestBody = mapper.writeValueAsString(this.request);
        mvc.perform(
                        put("/api/v1/products/{productId}", inventoryManagement.getProductUuid())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                                .header("Authorization", "invalid-token")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "test@test.com", password = "test@1234")
    public void updateProductShouldSucceedWhenValidTokenAndBodyArePassed() throws Exception {
        String requestBody = mapper.writeValueAsString(this.request);
        mvc.perform(
                        put("/api/v1/products/{productId}", inventoryManagement.getProductUuid())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                                .header("Authorization", UserManagementExtension.getUserToken())
                )
                .andExpect(status().isNoContent());
    }
}
