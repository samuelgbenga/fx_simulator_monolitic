package ng.samuel.fxmono.simulator.exception;


import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {
    public CustomException(String message) { super(message); }
    public abstract ErrorResponse serializeError();
    public abstract HttpStatus getStatus();
}
