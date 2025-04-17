package ng.samuel.fxmono.simulator.exception.exceptions;

import ng.samuel.fxmono.simulator.exception.CustomException;
import ng.samuel.fxmono.simulator.exception.ErrorResponse;
import org.springframework.http.HttpStatus;

public class IncorrectPasswordException extends CustomException {
    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private ErrorResponse errorResponse = new ErrorResponse();

    public IncorrectPasswordException(String message) {
        super("The password is incorrect");

        errorResponse.setMessage("The password is incorrect");
        errorResponse.setField(ErrorResponse.ErrorField.password.name());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
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
