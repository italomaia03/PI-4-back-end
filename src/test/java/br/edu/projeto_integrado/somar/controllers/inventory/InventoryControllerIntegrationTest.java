package br.edu.projeto_integrado.somar.controllers.inventory;

import br.edu.projeto_integrado.somar.extensions.UserManagementExtension;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(UserManagementExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class InventoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "test@test.com", password = "test@1234")
    public void testShowcaseWithNoFilters() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/showcase")
                        .header("Authorization", "Bearer " + UserManagementExtension.getUserToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(5));  // Verifying that 5 products exist
    }

    @Test
    @WithMockUser(username = "test@test.com", password = "test@1234")
    public void testShowcaseWithBarcodeFilter() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/showcase")
                        .param("barcode", "BARCODE1")
                        .header("Authorization", "Bearer " + UserManagementExtension.getUserToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Product 1"));
    }

    @Test
    @WithMockUser(username = "test@test.com", password = "test@1234")
    public void testShowcaseWithPriceRangeFilter() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/showcase")
                        .param("minBuyingPrice", "60")
                        .param("maxBuyingPrice", "70")
                        .header("Authorization", "Bearer " + UserManagementExtension.getUserToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Product 2"))
                .andExpect(jsonPath("$[0].sellingPrice").value(120));
    }

    @Test
    @WithMockUser(username = "test@test.com", password = "test@1234")
    public void getProductsWithPagination() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/showcase")
                        .param("page", "1")
                        .param("limit", "2")
                        .header("Authorization", "Bearer " + UserManagementExtension.getUserToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[1].name").value("Product 2"));
    }

    @Test
    @WithMockUser(username = "test@test.com", password = "test@1234")
    public void getProductsWithMultipleFilters() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/showcase")
                        .param("barcode", "BARCODE2")
                        .param("name", "Product 2")
                        .param("minAmount", "20")
                        .header("Authorization", "Bearer " + UserManagementExtension.getUserToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Product 2"))
                .andExpect(jsonPath("$[0].quantity").value(20));
    }

    @Test
    public void getProductsWithoutAuthenticationShouldFail() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/showcase")
                        .param("barcode", "BARCODE2")
                        .param("name", "Product 2")
                        .param("minAmount", "20")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

}

