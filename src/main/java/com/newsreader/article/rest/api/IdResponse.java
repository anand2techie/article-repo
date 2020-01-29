package com.newsreader.article.rest.api;

import lombok.NoArgsConstructor;
import org.jsondoc.core.annotation.ApiObject;

@ApiObject(name = "idResponse", description = "a reponse that contains an id only")
@NoArgsConstructor
public class IdResponse extends SimpleResponse<IdDTO> {

    public IdResponse(long id) {
        super(new IdDTO(createId(id)));
    }

    public IdResponse(IdDTO dto) {
        super(dto);
    }

    public IdResponse(WarningsValidationResult warnings, long id) {
        this(id);
        this.setWarnings(warnings);
    }

    public static EncryptedId createId(long id) {
        return new EncryptedId(id);
    }
}
