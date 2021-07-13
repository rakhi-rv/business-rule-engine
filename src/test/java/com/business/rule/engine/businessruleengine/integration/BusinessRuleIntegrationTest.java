package com.business.rule.engine.businessruleengine.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class BusinessRuleIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getBusinessRulesByProductTypeSuccessfulResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/businessrules/{paymentType}", "physical_product"))
                .andExpect(status().isOk())
                .andExpect(content().json(getExpectedResponse()));
    }

    @Test
    void getBusinessRulesByProductTypeErrorResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/businessrules/{paymentType}", "clothes"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"message\":\"Product not found\"}"));
    }

    private String getExpectedResponse() {
        return "[\n" +
                "  \"generate packing slip for shipping\",\n" +
                "  \"generate a commission payment to the agent\"\n" +
                "]";
    }
}
