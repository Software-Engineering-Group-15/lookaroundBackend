package lookaroundBackend.security.JwtToken;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;


public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -6597793291468730504L;

	private final String username;

	private String jwtString;

    public JwtAuthenticationToken(String username, String jwtString) {
		super(null);
		this.username = username;
		this.jwtString = jwtString;
		setAuthenticated(false);
	}

	public JwtAuthenticationToken(String username, String jwtString,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.username = username;
		this.jwtString = jwtString;
		super.setAuthenticated(true);
	}

	public String getUsername(){
		return this.username;
	}

	public String getJwtTokenString(){
		return this.jwtString;
	}


	@Override
	public Object getCredentials() {
		return this.jwtString;
	}

	@Override
	public Object getPrincipal() {
		return this.username;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		Assert.isTrue(!isAuthenticated,
				"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		super.setAuthenticated(false);
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		this.jwtString = null;
	}
}
