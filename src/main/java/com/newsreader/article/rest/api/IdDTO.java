package com.newsreader.article.rest.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jsondoc.core.annotation.ApiObject;

import java.io.Serializable;

/**
 * Used to return the id only, e.g. after a POST.
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiObject(name = "id", description = "the id of a database entity")
public class IdDTO implements Serializable {
    EncryptedId id;
}
