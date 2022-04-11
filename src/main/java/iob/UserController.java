package iob;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import iob.bounderies.NewUserBoundary;
import iob.bounderies.UserBoundary;
import iob.logic.UserConverter;
import iob.logic.UsersService;

@RestController
public class UserController {

	private UsersService userService;
	private UserConverter userConverter;

	@org.springframework.beans.factory.annotation.Autowired
	public UserController(UsersService userService, UserConverter userConverter) {
		this.userService = userService;
		this.userConverter = userConverter;
	}

	// Create a new user
	@RequestMapping(path = "/iob/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary createUser(@RequestBody NewUserBoundary newUserBoundary) {

		return userService.createUser(userConverter.newUserbToUserb(newUserBoundary));
	}

	// Login valid user and retrieve user details
	@RequestMapping(path = "/iob/users/login/{userDomain}/{userEmail}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary getUser(@PathVariable("userDomain") String userDomain,
			@PathVariable("userEmail") String userEmail) {
		System.err.println("User email: "+userEmail);
		return userService.login(userDomain, userEmail);
	}

	// Update user details
	@RequestMapping(path = "/iob/users/{userDomain}/{userEmail}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(@PathVariable("userDomain") String domain, @PathVariable("userEmail") String userEmail,
			@RequestBody UserBoundary userBoundary) {

		userService.updateUser(domain, userEmail, userBoundary);
	}
}
