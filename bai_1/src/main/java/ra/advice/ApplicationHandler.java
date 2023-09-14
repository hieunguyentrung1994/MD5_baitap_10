package ra.advice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.exception.UserAuthentication;

@RestControllerAdvice
public class ApplicationHandler {
	
	@ExceptionHandler(UserAuthentication.class)
	public ResponseEntity<String> handleExceptionLogin(UserAuthentication userAuthentication) {
		return new ResponseEntity<>(userAuthentication.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	
	
	
}
