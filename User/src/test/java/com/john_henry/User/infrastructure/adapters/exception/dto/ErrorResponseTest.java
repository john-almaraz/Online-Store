package com.john_henry.User.infrastructure.adapters.exception.dto;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class ErrorResponseTest {

    @Test
    void constructor_ShouldInitializeFieldsCorrectly() {
        int expectedStatus = 404;
        String expectedMessage = "Not Found";
        long expectedTimestamp = System.currentTimeMillis();

        ErrorResponse errorResponse = new ErrorResponse(expectedStatus, expectedMessage, expectedTimestamp);

        assertEquals(expectedStatus, errorResponse.getStatus());
        assertEquals(expectedMessage, errorResponse.getMessage());
        assertEquals(expectedTimestamp, errorResponse.getTimestamp());
    }

}