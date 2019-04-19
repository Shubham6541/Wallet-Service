package com.tw.pathashala.api.common;

import com.tw.pathashala.api.error.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ValidationMessageHandlerTest {

    @Test
    void expectErrorResponseToHaveReasonWhenExceptionHasSpecificMessage() {
        ValidationMessageHandler validationMessageHandler = new ValidationMessageHandler();
        String fieldName = "amount";
        String errorMessage = "should be greater than zero";

        ErrorResponse errorResponse = validationMessageHandler.handleValidationErrors(exceptionWithSpecificError(fieldName, errorMessage));

        assertEquals(errorResponse.getMessage(), ValidationMessageHandler.VALIDATION_FAILURE_MESSAGE);
        assertEquals(errorResponse.getReasons().size(), 1);
        assertEquals(errorResponse.getReasons().get(fieldName), errorMessage);
    }

    @Test
    void expectErrorResponseWithoutReasonWhenExceptionHasNoSpecificMessage() {
        ValidationMessageHandler validationMessageHandler = new ValidationMessageHandler();

        ErrorResponse errorResponse = validationMessageHandler.handleValidationErrors(exceptionWithoutSpecificError());

        assertEquals(errorResponse.getMessage(), ValidationMessageHandler.VALIDATION_FAILURE_MESSAGE);
        assertEquals(errorResponse.getReasons().size(), 0);
    }

    private MethodArgumentNotValidException exceptionWithSpecificError(String fieldName, String message) {
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(new FieldError("object", fieldName, message)));
        return new MethodArgumentNotValidException(null, bindingResult);
    }

    private MethodArgumentNotValidException exceptionWithoutSpecificError() {
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        when(bindingResult.getAllErrors()).thenReturn(Collections.emptyList());
        return new MethodArgumentNotValidException(null, bindingResult);
    }
}