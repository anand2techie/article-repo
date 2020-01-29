package com.newsreader.article.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotEmpty;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArticleDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    @NotEmpty
    String header;

    String shortDescription;

    @NotEmpty
    String text;

    Date publishDate;

    List<String> authors = new ArrayList<>();

    List<String> keywords = new ArrayList<>();
}
