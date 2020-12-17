package lookaroundBackend.security.token;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;


public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public JwtAuthenticationToken(String username, String jwt) {
		super(username, jwt);
	}

	public JwtAuthenticationToken(String username, String jwt,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, jwt, authorities);
	}

	public String getUsername(){
		return super.getName();
	}

	public String getJwtTokenString(){
		return (String) super.getCredentials();
	}

	private static final long serialVersionUID = -6597793291468730504L;

}
