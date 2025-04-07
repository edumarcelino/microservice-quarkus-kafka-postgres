package br.com.common.exception.generic;

public abstract class ApiException extends RuntimeException {
    private final String path;
    private final String value;

    public ApiException(String message, String path, String value) {
        super(message);
        this.path = path;
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public String getValue() {
        return value;
    }
}