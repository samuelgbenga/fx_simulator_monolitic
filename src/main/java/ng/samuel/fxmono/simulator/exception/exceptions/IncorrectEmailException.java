package ng.samuel.fxmono.simulator.exception.exceptions;

import ng.samuel.fxmono.simulator.exception.CustomException;
import ng.samuel.fxmono.simulator.exception.ErrorResponse;
import org.springframework.http.HttpStatus;

public class IncorrectEmailException extends CustomException {

    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private ErrorResponse errorResponse = new ErrorResponse();

    public IncorrectEmailException(String message) {
        super("This email does not exist in our record");

        errorResponse.setMessage("This email does not exist in our record");
        errorResponse.setField(ErrorResponse.ErrorField.email.name());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
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
