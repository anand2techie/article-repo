package com.newsreader.article.rest.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarningsValidationResult implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ValidationWarningDTO> semiMandatoryWarnings;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ValidationWarningDTO> otherWarnings;

    public int countSemiMandatoryWarnings() {

        return Optional.ofNullable(semiMandatoryWarnings).map(List::size).orElse(0);
    }
}
