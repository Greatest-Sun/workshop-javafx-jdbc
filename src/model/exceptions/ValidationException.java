package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {
    private Map<String, String> errors = new HashMap<>();

    public Map<String, String> getErrors() {
        return errors;
    }

    public void addErros(String fieldName, String errorName) {
        errors.put(fieldName, errorName);
    }

    public ValidationException(String s) {
        super(s);
    }
}
