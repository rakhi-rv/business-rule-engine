package com.business.rule.engine.businessruleengine.controller;

import com.business.rule.engine.businessruleengine.exception.DataNotFoundException;
import com.business.rule.engine.businessruleengine.service.BusinessRuleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureWebClient
class BusinessRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusinessRuleService businessRuleService;

    @Test
    void getBusinessRulesByProductTypeSuccessfulResponse() throws Exception {
        //given
        var rules = Set.of("generate packing slip for shipping",
                "generate a commission payment to the agent");

        given(businessRuleService.getRules("physical_product"))
                .willReturn(rules);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/businessrules/{paymentType}", "physical_product"))
                .andExpect(status().isOk())
                .andExpect(content().json(getExpectedResponse()));
    }

    @Test
    void getBusinessRulesByProductTypeDataNotFoundException() throws Exception {
        //given
        given(businessRuleService.getRules("physical_product"))
                .willThrow(new DataNotFoundException("Product not found"));

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/businessrules/{paymentType}", "physical_product"))
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