package ng.samuel.fxmono.simulator.exception.exceptions;

import ng.samuel.fxmono.simulator.exception.CustomException;
import ng.samuel.fxmono.simulator.exception.ErrorResponse;
import org.springframework.http.HttpStatus;


public class NotEnoughFundException extends CustomException {

    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private ErrorResponse errorResponse = new ErrorResponse();

    public NotEnoughFundException(String type) {
        super("You do not have sufficient fund to place this order");

        errorResponse.setMessage("You do not have sufficient fund to place this order");
        errorResponse.setField(type);
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
