package com.example.demo;

import com.example.demo.model.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/hello returns greeting and UP status")
    void shouldReturnGreeting() throws Exception {
        mockMvc.perform(get("/api/hello").param("name", "Developer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Hello, Developer!")))
                .andExpect(jsonPath("$.status", is("UP")));
    }

    @Test
    @DisplayName("GET /api/items returns pre-seeded items")
    void shouldReturnAllItems() throws Exception {
        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$[0].name", notNullValue()));
    }

    @Test
    @DisplayName("POST /api/items creates new item and returns 201 Created")
    void shouldCreateNewItem() throws Exception {
        Item newItem = new Item(null, "Wireless Headphones", "Noise cancelling", 199.99);

        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Wireless Headphones")))
                .andExpect(jsonPath("$.price", is(199.99)));
    }

    @Test
    @DisplayName("POST /api/items with invalid payload returns 400 Bad Request")
    void shouldFailValidationOnBlankName() throws Exception {
        Item invalidItem = new Item(null, "", "No name item", -5.0);

        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidItem)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.name", notNullValue()))
                .andExpect(jsonPath("$.validationErrors.price", notNullValue()));
    }

    @Test
    @DisplayName("GET /api/items/9999 returns 404 Not Found")
    void shouldReturn404WhenItemNotFound() throws Exception {
        mockMvc.perform(get("/api/items/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("Not Found")));
    }
}
