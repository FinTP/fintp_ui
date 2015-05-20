package ro.allevo.fintpui.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason="There was a database conflict while processing youre request!")
public class ConflictException extends RuntimeException{

	private static final long serialVersionUID = 2735806673061983386L;
	
	public ConflictException(String message) {
		super(message);
	}

}
