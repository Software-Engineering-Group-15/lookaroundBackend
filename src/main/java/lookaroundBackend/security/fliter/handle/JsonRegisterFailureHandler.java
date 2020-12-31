package lookaroundBackend.security.fliter.handle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;

import lookaroundBackend.security.fliter.exception.AuthenticationFormatException;
import lookaroundBackend.security.fliter.exception.UsernameRegisteredException;

public class JsonRegisterFailureHandler extends JsonLoginFailureHandler {

    public JsonRegisterFailureHandler() {
        super();
        getFailureUrlMap().clear();
        // 格式错误
        getFailureUrlMap().put(AuthenticationFormatException.class.getName(), "/register/error/formatError");
        // 用户名已注册
        getFailureUrlMap().put(UsernameRegisteredException.class.getName(), "/register/error/usernameRegistered");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        super.onAuthenticationFailure(request, response, exception);
    }
}
