package ng.samuel.fxmono.simulator.exception.exceptions;

import ng.samuel.fxmono.simulator.exception.CustomException;
import ng.samuel.fxmono.simulator.exception.ErrorResponse;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends CustomException {
    private final HttpStatus status = HttpStatus.UNAUTHORIZED;
    private final ErrorResponse errorResponse = new ErrorResponse();

    public InvalidTokenException(String message) {
        super(message);

        errorResponse.setMessage(message);
        errorResponse.setField(ErrorResponse.ErrorField.token.name());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    @Override
    public ErrorResponse serializeError() {
        return errorResponse;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}
