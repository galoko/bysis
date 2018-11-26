package com.tests.bisys;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CustomErrorController implements ErrorController {

    private static final String ERROR_MAPPING = "/error";
    
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class NotFound extends RuntimeException {
		
		NotFound(String message) {
			super(message);
		}
	}

    @RequestMapping(value = ERROR_MAPPING)
    public ResponseEntity<String> error() {
        throw new NotFound("Resource not found");
    }

    @Override
    public String getErrorPath() {
        return ERROR_MAPPING;
    }
}