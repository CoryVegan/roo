package io.aweris.roo.infrastructure.rest.error;

import org.springframework.http.HttpStatus;

public class ValidationError extends ApiError {

	public ValidationError() {
		super(HttpStatus.BAD_REQUEST);
		setCode("VALIDATION");
	}
}
