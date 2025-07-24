package dev.pantanal.catalogo.common;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class GlobalExceptionHandlerTest {

    private MethodArgumentNotValidException createMockValidationException() {
        BindingResult bindingResult = mock(BindingResult.class);
        List<FieldError> fieldErrors = Arrays.asList(
                new FieldError("objectName", "field1", "must not be null"),
                new FieldError("objectName", "field2", "must not be blank"));
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);
        return ex;
    }

    @Test
    void handleValidationExceptions_assertHttpStatus() {
        MethodArgumentNotValidException ex = createMockValidationException();
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        ResponseEntity<Map<String, Object>> response = handler.handleValidationExceptions(ex);

        assertNotNull(response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleValidationExceptions_assertBody() {
        MethodArgumentNotValidException ex = createMockValidationException();
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        ResponseEntity<Map<String, Object>> response = handler.handleValidationExceptions(ex);

        Map<String, Object> body = response.getBody();

        assertNotNull(body);
        assertEquals(3, body.size());
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.get("status"));
        assertEquals("Bad Request", body.get("error"));
        List<?> messages = (List<?>) body.get("messages");
        assertNotNull(messages);
        assertTrue(messages.contains("field1: must not be null"));
        assertTrue(messages.contains("field2: must not be blank"));
    }
}
