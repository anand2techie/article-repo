package com.newsreader.article.rest.api;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO that is used to return query results from the service layer to the controllers layer.
 */
@Value
public class PageDTO<D> implements Serializable {
    /**
     * The current page number.
     */
    int page;

    /**
     * The size of the current page.
     */
    int pageSize;

    /**
     * The total count of all available results.
     */
    long count;

    /**
     * The data of the requested page.
     */
    List<D> data;

    public PageDTO(int page, int pageSize, long count, List<D> data) {
        this.page = page;
        this.pageSize = pageSize;
        this.count = count;
        this.data = data;
    }
}
