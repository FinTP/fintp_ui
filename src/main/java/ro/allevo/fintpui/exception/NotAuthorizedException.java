package ro.allevo.fintpui.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason="Not enough rights to perform this action")
public class NotAuthorizedException extends RuntimeException{

	private static final long serialVersionUID = 2735806673061983386L;
	
	public NotAuthorizedException(String message) {
		super(message);
	}

}
