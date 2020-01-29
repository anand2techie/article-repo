package com.newsreader.article.rest.api;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.io.Serializable;

/**
 * Used to return an error message.
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiObject(name = "error", description = "Used to return an error message.")
public class ErrorDTO implements Serializable {
    @ApiObjectField(description = "the error message key - needs to be translated by the client")
    String message;

    public ErrorDTO(String message) {
        this.message = message;
    }

    //default for jackson-mapping
    public ErrorDTO() {
        this.message = "";
    }
}
