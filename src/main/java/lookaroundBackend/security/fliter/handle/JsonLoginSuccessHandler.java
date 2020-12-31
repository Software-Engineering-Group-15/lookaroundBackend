package lookaroundBackend.security.fliter.handle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;

import lookaroundBackend.security.token.JwtTokenUtil;

public class JsonLoginSuccessHandler extends ForwardAuthenticationSuccessHandler {

    private final static String forwardUrl = "/login/success";

    public JsonLoginSuccessHandler() {
        super(forwardUrl);
    }

    public JsonLoginSuccessHandler(String forwardUrl) {
        super(forwardUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String token = JwtTokenUtil.createJwtToken(authentication);
        response.setHeader("Authorization", token);
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
