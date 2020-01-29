package com.newsreader.article.rest.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiObject(name = "listResponse", description = "a reponse that contains a list of elements along"
    + " with meta data for paging.")
public class ListResponse<D> extends SimpleResponse<D> implements Serializable {
    @ApiObjectField(description = "additional information on the list")
    ListMetaData meta;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Set<String> allowedActions;

    public ListResponse(List<D> data) {
        this(new PageDTO<>(0, data.size(), data.size(), data), emptyList());
    }

    public ListResponse(List<D> data, HateoasLink... links) {
        this(new PageDTO<>(0, data.size(), data.size(), data), asList(links));
    }

    public ListResponse(PageDTO<D> page) {
        this(page, emptyList());
    }

    public ListResponse(PageDTO<D> page, HateoasLink... links) {
        this(page, asList(links));
    }

    public ListResponse(PageDTO<D> page, List<HateoasLink> links) {
        super(null, links);
        setData(page.getData());
        this.meta = new ListMetaData(page.getPage(), page.getPageSize(), page.getCount());
    }

    @Value
    @ApiObject(name = "listMetaData", description = "The paging meta data that is returned in a "
        + "ListResponse.")
    public static class ListMetaData implements Serializable {
        @ApiObjectField(description = "The requested page number.")
        int page;

        @ApiObjectField(description = "The requested page size.")
        int pageSize;

        @ApiObjectField(description = "The total count of items.")
        long count;

        //default for jackson-mapping
        public ListMetaData() {
            this.page = 0;
            this.pageSize = 0;
            this.count = 0;
        }

        public ListMetaData(int page, int pageSize, long count) {
            this.page = page;
            this.pageSize = pageSize;
            this.count = count;
        }
    }
}
