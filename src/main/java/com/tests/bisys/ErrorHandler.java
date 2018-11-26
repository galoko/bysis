package com.tests.bisys;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {
	
	private static final Logger LOGGER = Logger.getLogger(ErrorHandler.class.getName());

    public static final String DEFAULT_ERROR_VIEW = "error";
    
    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ResponseEntity<ExceptionWrapper> defaultErrorHandler(HttpServletRequest request, Exception e) {
        	
    	HttpStatus code = HttpStatus.BAD_REQUEST;
    	
    	ResponseStatus annotation = AnnotatedElementUtils.findMergedAnnotation(e.getClass(), ResponseStatus.class);
    	if (annotation != null)
    		code = annotation.code();
    	
    	if (code.isError())
    		LOGGER.warning(String.format("Error \"%s\" occurred, code is %d.", e.getMessage(), code.value()));
    	
        return new ResponseEntity<ExceptionWrapper>(new ExceptionWrapper(e, code), code);
    }
}