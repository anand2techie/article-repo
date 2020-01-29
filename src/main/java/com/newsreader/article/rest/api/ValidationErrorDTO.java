package com.newsreader.article.rest.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.io.Serializable;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@ApiObject(name = "validationError")
@NoArgsConstructor
public class ValidationErrorDTO extends ValidationAnomalyDTO implements Serializable {

    @ApiObjectField(description = "The allowed minimum value, if eligible.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Object minValue;

    @ApiObjectField(description = "The allowed maximum value, if eligible.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Object maxValue;

    @ApiObjectField(description = "The matching regular expression, if eligible.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String matchingRegEx;

    @ApiObjectField(description = "The allowed maximum length, if eligible.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer maxLength;

    public ValidationErrorDTO(String message, List<String> fields, Object rejectedValue) {
        super(message, fields, rejectedValue);
    }

    public ValidationErrorDTO(String message, String field, Object rejectedValue) {
        super(message, field, rejectedValue);
    }

    public ValidationErrorDTO(String message) {
        super(message);
    }
}
