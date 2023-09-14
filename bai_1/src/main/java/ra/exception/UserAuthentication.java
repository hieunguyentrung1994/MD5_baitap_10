package ra.exception;

import javax.security.sasl.AuthenticationException;

public class UserAuthentication extends Exception {
	
	public UserAuthentication(String message) {
		super(message);
	}
}
