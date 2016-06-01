package in.tn.mobilepay.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import in.tn.mobilepay.dao.UserDAOImpl;
import in.tn.mobilepay.entity.UserEntity;
import in.tn.mobilepay.services.ServiceUtil;

@Component("authenticationProvider")
public class AuthenticationProviderImpl  implements AuthenticationProvider {
	
	@Autowired
	private UserDAOImpl userDao;
	
	@Autowired
	private ServiceUtil serviceUtil;
    
	
	private static final Logger logger = Logger.getLogger(AuthenticationProviderImpl.class);
	/**
     * Validate Given username and password. 
     */
	@Override
	@Transactional(readOnly=true)
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        Integer userId = userDao.getUserEnityByToken(name, password);
        logger.info("userEntity db:"+userId);
        if (userId == null)
		      throw new UsernameNotFoundException("Invalid User");
		 
       
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication auth = new UsernamePasswordAuthenticationToken(userId, password, grantedAuths);
        return auth;
        
	
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
