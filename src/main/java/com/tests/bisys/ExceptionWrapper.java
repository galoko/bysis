package com.tests.bisys;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.http.HttpStatus;

@XmlRootElement(name = "error")
public class ExceptionWrapper {
	
	private final String message;
	private final int code;
	private final String codeName;
	
	public ExceptionWrapper(Exception ex, HttpStatus status) {
		
		message = ex.getMessage();
		code = status.value();
		codeName = status.name();
	}
	
	public ExceptionWrapper() {
		
		message = "";
		code = 0;
		codeName = "";
	}
	
	@XmlAttribute(name = "message")
	public String getMessage() {
		return message;
	}
	
	@XmlAttribute(name = "code")
	public int getCode() {
		return code;
	}
	
	@XmlAttribute(name = "codename")
	public String getCodeName() {
		return codeName;
	}
}