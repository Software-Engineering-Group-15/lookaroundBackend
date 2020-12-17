package lookaroundBackend.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lookaroundBackend.security.token.JwtAuthenticationToken;
import lookaroundBackend.security.token.JwtTokenUtil;


public class JwtAuthenticationProvider implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    Authentication authenResult = null;
    try{
      authenResult = JwtTokenUtil.parseJwtToken((JwtAuthenticationToken) authentication);
    }
    catch(Exception e){
      // TODO: 定义一个新的异常
      throw new UsernameNotFoundException(e.toString() + " : JWT Authentication Fail");
    }
    return authenResult;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return JwtAuthenticationToken.class.isAssignableFrom(authentication);
  }

}
