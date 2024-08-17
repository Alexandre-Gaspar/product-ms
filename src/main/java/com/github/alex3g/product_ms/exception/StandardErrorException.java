package com.github.alex3g.product_ms.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StandardErrorException {
    private Instant timestamp;
    private int status;
    private String error;
    private String path;
    private String message;
    private Map<String, String> messages;
}
