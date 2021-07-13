package com.business.rule.engine.businessruleengine.controller;

import com.business.rule.engine.businessruleengine.service.BusinessRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Set;

@RestController
@Slf4j
@Validated
@RequestMapping("/")
@RequiredArgsConstructor
public class BusinessRuleController {

    private final BusinessRuleService businessRuleService;

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(path = "/businessrules/{paymentType}", produces = {"application/json"})
    public Set<String> getBusinessRules(@NotNull @PathVariable(value = "paymentType") String paymentType) {
        return businessRuleService.getRules(paymentType);
    }
}
