package com.newsreader.article.rest.api;

public class ValidationErrorCodes {
    public static final String EXCEEDS_MAX_LENGTH_EXCEPTION_CODE =
        "attribute.validation.tooManyCharacters";
    public static final String EXCEEDS_MAX_VALUE_EXCEPTION_CODE =
        "attribute.validation.exceeds.maxValue";
    public static final String EXCEEDS_MIN_VALUE_EXCEPTION_CODE =
        "attribute.validation.notEnoughCharacters";
    public static final String REG_EXP_MATCH_EXCEPTION_CODE = "attribute.validation.invalidRegexp";
}
