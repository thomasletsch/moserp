package org.moserp.infrastructure.authentication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/")
public class AuthUserController {

	/**
	 * Return the principal identifying the logged in user
	 * @param user
	 * @return
	 */
	@RequestMapping("/user")
	public Principal getCurrentLoggedInUser(Principal user) {
		return user;
	}
}
