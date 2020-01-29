package com.newsreader.article.rest.api;

import lombok.Value;

import java.io.Serializable;

@Value
public final class HateoasLink implements Serializable {
    String rel;
    String href;
}
