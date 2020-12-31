package lookaroundBackend.security.fliter.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

public class AuthenticationFormatException extends AuthenticationServiceException{

    public AuthenticationFormatException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthenticationFormatException(String msg) {
        super(msg);
    }
    
    /**
     *
     */
    private static final long serialVersionUID = -8428003488340508014L;

}
