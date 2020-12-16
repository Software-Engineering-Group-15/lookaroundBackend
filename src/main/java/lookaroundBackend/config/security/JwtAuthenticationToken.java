package lookaroundBackend.config.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;


public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public JwtAuthenticationToken(String username, Object credentials) {
		super(username, credentials);
	}

	public JwtAuthenticationToken(String username, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, credentials, authorities);
	}

	private static final long serialVersionUID = -6597793291468730504L;

}
