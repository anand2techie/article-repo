package com.newsreader.article.rest.api;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO that is used to serialize/deserialize encrypted ids to/from the frontend.
 */
@Data
@NoArgsConstructor
public class EncryptedId implements Serializable {

    /**
     * The id of the current entity
     */
    private long id;

    public EncryptedId(long id) {
        this.id = id;
    }
}
