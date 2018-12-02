package io.aweris.roo.infrastructure.rest.error;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ApiError {

	public ApiError(HttpStatus httpStatus) {
		this.status = httpStatus.getReasonPhrase();
	}

	String code;
	String status;
	String message;
	Object details;
}
