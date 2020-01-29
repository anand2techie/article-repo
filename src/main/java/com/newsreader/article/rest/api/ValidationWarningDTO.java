package com.newsreader.article.rest.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ValidationWarningDTO extends ValidationAnomalyDTO {

    public ValidationWarningDTO(String message, List<String> fields, Object rejectedValue) {

        super(message, fields, rejectedValue);
    }

    public ValidationWarningDTO(String message, String field, Object rejectedValue) {

        super(message, field, rejectedValue);
    }

    public ValidationWarningDTO(String message) {

        super(message);
    }
}
