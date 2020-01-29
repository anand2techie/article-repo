package com.newsreader.article.rest.api;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@Value
@EqualsAndHashCode(callSuper = true)
public final class ErrorResponse extends Response {
    List<? extends ErrorDTO> errors;

    //default for jackson-mapping
    public ErrorResponse() {
        this(new ArrayList<>());
    }

    public ErrorResponse(List<? extends ErrorDTO> errors) {
        super(emptyList());
        this.errors = errors;
    }

    public ErrorResponse(ErrorDTO error) {
        this(singletonList(error));
    }

    public ErrorResponse(String message) {
        this(new ErrorDTO(message));
    }
}
