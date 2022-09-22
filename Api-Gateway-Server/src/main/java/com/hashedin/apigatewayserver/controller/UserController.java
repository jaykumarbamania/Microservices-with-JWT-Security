package com.hashedin.apigatewayserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hashedin.apigatewayserver.model.DAOUser;
import com.hashedin.apigatewayserver.model.UserDTO;
import com.hashedin.apigatewayserver.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    
   
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
//		if(user.getRole().equals(ROLE.ADMIN))
			return ResponseEntity.ok(userService.save(user));
//		return ResponseEntity.badRequest().body("You do not have admin access");
	}
}