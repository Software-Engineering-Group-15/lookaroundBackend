package lookaroundBackend.config.security;

import java.io.IOException;
import java.util.ArrayList;

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

public class JsonLoginFilter extends AbstractAuthenticationProcessingFilter{


    public JsonLoginFilter(AuthenticationManager authenticationManager) {
		super(new AntPathRequestMatcher("/login","POST"), authenticationManager);
	}
    /**
     * 实现JSON登录. 登录格式：UserBean
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException,IOException {
        if (request.getContentType() == null){
            return null;
        }
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper mapper = new ObjectMapper();
            UserBean user = mapper.readValue(request.getInputStream(), UserBean.class);
            String username = user.getUsername();
            username = (username != null) ? username : "";
            username = username.trim();
            String password = user.getPassword();
            password = (password != null) ? password : "";

            // 交给数据库处理
            UsernamePasswordAuthenticationToken authRequest = 
                new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());

            return this.getAuthenticationManager().authenticate(authRequest);

        }
        return null;
    }

}
