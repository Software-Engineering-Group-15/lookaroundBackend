package lookaroundBackend.security.fliter.exception;

import org.springframework.security.core.AuthenticationException;

public class UsernameRegisteredException extends AuthenticationException {

    public UsernameRegisteredException(String msg) {
        super(msg);
    }

	public UsernameRegisteredException(String msg, Throwable cause) {
		super(msg, cause);
    }
    
    /**
     *
     */
    private static final long serialVersionUID = -2745069038126264981L;


}
