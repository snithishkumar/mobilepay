package in.tn.mobilepay.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class MobilePayAuthenticationFilter extends BasicAuthenticationFilter {

	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
	public MobilePayAuthenticationFilter(AuthenticationManager authenticationManager,AuthenticationEntryPoint authenticationEntryPoint) {
		super(authenticationManager,authenticationEntryPoint);
		
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final boolean debug = this.logger.isDebugEnabled();

		String header = request.getHeader("mobilePay");
		String accessToken = request.getHeader("accessToken");
		String serverToken = request.getHeader("serverToken");
		
		try {
			if(header != null && accessToken != null && !accessToken.isEmpty() && serverToken != null && !serverToken.isEmpty()){
				
				
				if (authenticationIsRequired(accessToken)) {
					UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(accessToken,
							serverToken);
					authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
					Authentication authResult = this.getAuthenticationManager().authenticate(authRequest);

					if (debug) {
						this.logger.debug("Authentication success: " + authResult);
					}

					SecurityContextHolder.getContext().setAuthentication(authResult);

					onSuccessfulAuthentication(request, response, authResult);
				}
			}
			
		} catch (AuthenticationException failed) {
			SecurityContextHolder.clearContext();

			if (debug) {
				this.logger.debug("Authentication request for failed: " + failed);
			}

			onUnsuccessfulAuthentication(request, response, failed);

			if (isIgnoreFailure()) {
				chain.doFilter(request, response);
			} else {
				this.getAuthenticationEntryPoint().commence(request, response, failed);
			}

			return;
		}

		chain.doFilter(request, response);
	}

	private boolean authenticationIsRequired(String username) {
		// Only reauthenticate if username doesn't match SecurityContextHolder
		// and user
		// isn't authenticated
		// (see SEC-53)
		Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();

		if (existingAuth == null || !existingAuth.isAuthenticated()) {
			return true;
		}

		// Limit username comparison to providers which use usernames (ie
		// UsernamePasswordAuthenticationToken)
		// (see SEC-348)

		if (existingAuth instanceof UsernamePasswordAuthenticationToken && !existingAuth.getName().equals(username)) {
			return true;
		}

		// Handle unusual condition where an AnonymousAuthenticationToken is
		// already
		// present
		// This shouldn't happen very often, as BasicProcessingFitler is meant
		// to be
		// earlier in the filter
		// chain than AnonymousAuthenticationFilter. Nevertheless, presence of
		// both an
		// AnonymousAuthenticationToken
		// together with a BASIC authentication request header should indicate
		// reauthentication using the
		// BASIC protocol is desirable. This behaviour is also consistent with
		// that
		// provided by form and digest,
		// both of which force re-authentication if the respective header is
		// detected (and
		// in doing so replace
		// any existing AnonymousAuthenticationToken). See SEC-610.
		if (existingAuth instanceof AnonymousAuthenticationToken) {
			return true;
		}

		return false;
	}

}
