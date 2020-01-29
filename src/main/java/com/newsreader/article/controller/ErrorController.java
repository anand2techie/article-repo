package com.newsreader.article.controller;

import com.newsreader.article.exception.ArticleNotFoundException;
import com.newsreader.article.rest.api.ErrorResponse;
import com.newsreader.article.rest.api.ValidationErrorCodes;
import com.newsreader.article.rest.api.ValidationErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class ErrorController {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    ErrorResponse handleArticleNotFoundException(ArticleNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    protected ErrorResponse handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ValidationErrorDTO> messages = new ArrayList<>();
        bindingResult.getFieldErrors().stream().map(this::mapFieldError).forEach(messages::add);
        bindingResult.getGlobalErrors().stream().map(this::mapGlobalError).forEach(messages::add);
        return new ErrorResponse(messages);
    }

    private Object getRejectedValue(FieldError error) {

        Object rejectedValue = error.getRejectedValue();

        if ((rejectedValue instanceof Double && rejectedValue.equals(Double.NaN))
            || (rejectedValue instanceof Float && rejectedValue.equals(Float.NaN))) {
            return "NaN";
        }
        return rejectedValue;
    }

    private ValidationErrorDTO mapFieldError(FieldError error) {
        Object rejectedValue = getRejectedValue(error);
        String code = error.getCode();
        Object[] args = error.getArguments();
        if (code.startsWith("attribute.validation.")) {
            code = code.replace("attribute.validation.", "validation.");
        }
        if (code.startsWith("Size")) {
            return String.valueOf(rejectedValue).length() > (Integer) error.getArguments()[1]
                ?
                new ValidationErrorDTO(
                    messageSource.getMessage("SizeMax", args, null),
                    Collections.singletonList(error.getField()),
                    rejectedValue)
                :
                new ValidationErrorDTO(
                    messageSource.getMessage("SizeMin", args, null),
                    Collections.singletonList(error.getField()),
                    rejectedValue);
        } else {
            return new ValidationErrorDTO(messageSource.getMessage(error, null),
                Collections.singletonList(error.getField()),
                rejectedValue);
        }
    }

    protected ValidationErrorDTO mapGlobalError(ObjectError error) {
        String code = error.getCode();
        if (code.startsWith("attribute.validation.")) {
            Object[] args = error.getArguments();
            @SuppressWarnings("unchecked")
            List<String> attributeNames = (List<String>) args[0];
            ValidationErrorDTO result = new ValidationErrorDTO(
                code.substring("attribute.".length()),
                attributeNames,
                args[1] /* rejected value */
            );
            switch (code) {
                case ValidationErrorCodes.EXCEEDS_MAX_LENGTH_EXCEPTION_CODE:
                    result.setMaxLength((Integer) args[2]);
                    break;

                case ValidationErrorCodes.EXCEEDS_MAX_VALUE_EXCEPTION_CODE:
                    result.setMaxValue(args[2]);
                    break;

                case ValidationErrorCodes.EXCEEDS_MIN_VALUE_EXCEPTION_CODE:
                    result.setMinValue(args[2]);
                    break;

                case ValidationErrorCodes.REG_EXP_MATCH_EXCEPTION_CODE:
                    result.setMatchingRegEx((String) args[2]);
                    break;
                default:
                    break;
            }
            return result;
        } else {
            return new ValidationErrorDTO(code);
        }
    }

}
