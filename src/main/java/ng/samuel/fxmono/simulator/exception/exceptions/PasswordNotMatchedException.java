package ng.samuel.fxmono.simulator.exception.exceptions;

import ng.samuel.fxmono.simulator.exception.CustomException;
import ng.samuel.fxmono.simulator.exception.ErrorResponse;
import org.springframework.http.HttpStatus;

public class PasswordNotMatchedException extends CustomException {

    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private ErrorResponse errorResponse = new ErrorResponse();

    public PasswordNotMatchedException(String message) {
        super("The passwords do not match");

        errorResponse.setMessage("The passwords do not match");
        errorResponse.setField(ErrorResponse.ErrorField.confirm_password.name());
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
