package com.newsreader.article.rest.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jsondoc.core.annotation.ApiObjectField;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class ValidationAnomalyDTO extends ErrorDTO implements Serializable {

    /**
     * The fields that produced the error.
     * Optional, can be empty if the originating error was an {@link org.springframework.validation.ObjectError}.
     */
    @ApiObjectField(description = "The fields that produced the error.")
    private List<String> fields;

    /**
     * The value that was rejected.
     * Optional, can be empty if the originating error was an {@link org.springframework.validation.ObjectError}.
     */
    @ApiObjectField(description = "The value that was rejected.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object rejectedValue;

    protected ValidationAnomalyDTO(String message, List<String> fields, Object rejectedValue) {
        super(message);
        this.fields = fields;
        this.rejectedValue = rejectedValue;
    }

    protected ValidationAnomalyDTO(String message, String field, Object rejectedValue) {
        this(message, Collections.singletonList(field), rejectedValue);
    }

    protected ValidationAnomalyDTO(String message) {
        this(message, Collections.emptyList(), null);
    }

}
