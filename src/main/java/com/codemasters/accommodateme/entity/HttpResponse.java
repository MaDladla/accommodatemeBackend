package com.codemasters.accommodateme.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

/*******************************************************************************
 * @author Katlego Molala
 * Copyright (c) 2024.
 */


@Data
@SuperBuilder
@JsonInclude(NON_DEFAULT)
public class HttpResponse {
    protected String timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String reason;
    protected String token;
    protected String refreshToken;
    protected String message;
    protected String developerMessage;
    protected Map<?, ?> data;
}
