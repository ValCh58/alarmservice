package eis.com.alarmservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceDeletionErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceDeletionErrorException(String message){
        super(message);
    }

}
