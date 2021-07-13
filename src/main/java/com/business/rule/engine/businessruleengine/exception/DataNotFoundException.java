package com.business.rule.engine.businessruleengine.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataNotFoundException extends RuntimeException {

    private String message;
}
