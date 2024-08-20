package com.jersson.arrivasplata.agrrisk.api.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@GetMapping("/login")
	public String welcome(){
		return "Spring Boot + Azure Active Directory auth example !!! DEMO";
	}

}
