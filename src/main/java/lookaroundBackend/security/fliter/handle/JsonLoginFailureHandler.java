package lookaroundBackend.security.fliter.handle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import lookaroundBackend.security.fliter.exception.AuthenticationFormatException;

public class JsonLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final Map<String, String> failureUrlMap = new HashMap<>();
    private final static String defaultFailureUrl = "/error";

    public JsonLoginFailureHandler() {
        super(defaultFailureUrl);
        setUseForward(true);
        // 格式错误
        failureUrlMap.put(AuthenticationFormatException.class.getName(), "/login/error/formatError");
        // 凭证错误
        failureUrlMap.put(BadCredentialsException.class.getName(), "/login/error/badCredentials");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        this.logger.info("exception: " + exception.toString());
        
        request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);        
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        String url = this.failureUrlMap.get(exception.getClass().getName());
		if (url != null) {
            request.getRequestDispatcher(url).forward(request, response);
		}
		else {
			super.onAuthenticationFailure(request, response, exception);
		}
    }
}
