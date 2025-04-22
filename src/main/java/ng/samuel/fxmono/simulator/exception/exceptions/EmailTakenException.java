package ng.samuel.fxmono.simulator.exception.exceptions;

import lombok.NoArgsConstructor;
import ng.samuel.fxmono.simulator.exception.CustomException;
import ng.samuel.fxmono.simulator.exception.ErrorResponse;
import org.springframework.http.HttpStatus;


public class EmailTakenException extends CustomException {
    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private ErrorResponse errorResponse = new ErrorResponse();

    public EmailTakenException() {
        super("This email address is already used by another user");

        errorResponse.setMessage("This email address is already used by another user");
        errorResponse.setField(ErrorResponse.ErrorField.email.name());
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
