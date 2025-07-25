package dev.pantanal.catalogo.common;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import org.springframework.core.MethodParameter;

import org.springframework.lang.NonNull;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

@ControllerAdvice
public class TrimStringsRequestBodyAdvice extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Type targetType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    @NonNull
    public Object afterBodyRead(@NonNull Object body, @NonNull HttpInputMessage inputMessage,
            @NonNull MethodParameter parameter,
            @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        if (body != null) {
            trimStringFields(body);
        }
        return body;
    }

    private void trimStringFields(Object object) {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType().equals(String.class)) {
                field.setAccessible(true);
                try {
                    String value = (String) field.get(object);
                    if (value != null) {
                        field.set(object, value.trim());
                    }
                } catch (IllegalAccessException ignored) {
                }
            }
        }
    }
}
