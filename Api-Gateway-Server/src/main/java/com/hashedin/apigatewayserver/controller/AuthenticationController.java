package com.hashedin.apigatewayserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hashedin.apigatewayserver.config.JwtTokenUtil;
import com.hashedin.apigatewayserver.model.DAOUser;
import com.hashedin.apigatewayserver.model.JwtRequest;
import com.hashedin.apigatewayserver.model.JwtResponse;
import com.hashedin.apigatewayserver.model.UserDTO;
import com.hashedin.apigatewayserver.service.UserService;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest loginUser) 
              throws Exception {
//        final Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginUser.getUsername(),
//                        loginUser.getPassword()
//                )
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        final UserDetails user = userService.loadUserByUsername(loginUser.getUsername());
//        final String token = jwtTokenUtil.generateToken(user);
//        return ResponseEntity.ok(new JwtResponse(token));
    	

		authenticate(loginUser.getUsername(), loginUser.getPassword());

		final UserDetails userDetails = userService
				.loadUserByUsername(loginUser.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
    }
    
    private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}