package lookaroundBackend.security.fliter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lookaroundBackend.JsonBean.UserBean;
import lookaroundBackend.security.fliter.exception.AuthenticationFormatException;

public class JsonLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JsonLoginFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/**/login", "POST"), authenticationManager);
    }

    /**
     * 实现JSON登录. 登录格式：UserBean
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        // content的类型不是JSON，验证失败
        if (request.getContentType() == null || !request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            throw new AuthenticationFormatException("Authentication method not supported: Must be JSON");
        }

        ObjectMapper mapper = new ObjectMapper();
        UserBean user = mapper.readValue(request.getInputStream(), UserBean.class);
        String username = user.getUsername();
        username = (username != null) ? username : "";
        username = username.trim();
        String password = user.getPassword();
        password = (password != null) ? password : "";

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        return this.getAuthenticationManager().authenticate(authRequest);

    }

}
