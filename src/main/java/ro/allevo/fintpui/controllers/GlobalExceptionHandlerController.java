package ro.allevo.fintpui.controllers;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import ro.allevo.fintpui.exception.NotAuthorizedException;
import ro.allevo.fintpui.exception.TestException;

@ControllerAdvice
public class GlobalExceptionHandlerController {
	
	public static final String DEFAULT_ERROR_VIEW = "tiles/error";

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(PSQLException.class)
	public void handlePSQLException(){
		System.out.println("=====================");
		System.out.println("CONNECTOIN PROBLEM");
		System.out.println("=====================");
	}
	
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ExceptionHandler(NotAuthorizedException.class)
	public ModelAndView handleNotAuthorized(Exception exception){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", exception.getMessage());
		modelAndView.setViewName(DEFAULT_ERROR_VIEW);
		return modelAndView;
	}
	

	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ExceptionHandler({TestException.class})
	public void test(){
		System.out.println("=====================");
		System.out.println("TEST");
		System.out.println("=====================");
	}
}
