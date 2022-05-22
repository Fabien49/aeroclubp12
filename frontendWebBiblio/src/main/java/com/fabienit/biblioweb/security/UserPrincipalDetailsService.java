package com.fabienit.biblioweb.security;

import com.fabienit.biblioweb.model.beans.RegisteredUserBean;
import com.fabienit.biblioweb.web.proxies.ApiProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserPrincipalDetailsService
 */
@Service
public class UserPrincipalDetailsService implements UserDetailsService {

        @Autowired
        private ApiProxy apiProxy;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Check if fied email is empty            
        if (email.isEmpty()){
            String msg = "Username is empty";
            logger.debug(msg);
            throw new UsernameNotFoundException(msg);
        } 
        
        // Load registered user bean corresponding to input email in login form
        RegisteredUserBean user = apiProxy.findUserByEmail(email);

        // Check if user has been found in database
        if(user == null){
            String msg = "No user found with email " + email;
            logger.debug(msg);
            throw new UsernameNotFoundException(msg);
        } 

        // Init new user principal with loaded registered user bean
        UserPrincipal userPrincipal = new UserPrincipal(user);
        
        return userPrincipal;
    }

    
}