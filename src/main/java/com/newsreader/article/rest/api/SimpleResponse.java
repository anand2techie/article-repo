package com.newsreader.article.rest.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

/**
 * Simple response object that is used to return data.
 */
@EqualsAndHashCode(callSuper = false)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleResponse<D> extends Response {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<D> data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    WarningsValidationResult warnings;

    /**
     * The body of the response may be emtpy for update operations.
     */
    public SimpleResponse() {
        super(emptyList());
    }

    public SimpleResponse(D data) {
        this(data, emptyList());
    }

    public SimpleResponse(D data, List<HateoasLink> links) {
        super(links);
        this.data = data == null ? emptyList() : Collections.singletonList(data);
    }

    public SimpleResponse(D data, HateoasLink... links) {
        this(data, asList(links));
    }

    public SimpleResponse(WarningsValidationResult warnings, D data) {
        this(data, emptyList());
        setWarnings(warnings);
    }
}
