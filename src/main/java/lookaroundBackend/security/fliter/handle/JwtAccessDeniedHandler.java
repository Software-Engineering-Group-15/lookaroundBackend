package lookaroundBackend.security.fliter.handle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private static final String errorPage = "/error";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, accessDeniedException);  
        response.setStatus(HttpStatus.FORBIDDEN.value());
		request.getRequestDispatcher(JwtAccessDeniedHandler.errorPage).forward(request, response);
    }
}
