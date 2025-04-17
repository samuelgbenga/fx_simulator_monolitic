package ng.samuel.fxmono.simulator.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
    private String field;
    public ErrorResponse() {}

    public enum ErrorField {
        password, email, confirm_password, token
    }
}