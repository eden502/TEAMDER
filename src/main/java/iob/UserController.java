package iob;



import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import iob.bounderies.NewUserBoundary;
import iob.bounderies.UserBoundary;
import iob.bounderies.UserId;
import iob.logic.UserConverter;
import iob.logic.UsersService;
 
@RestController
public class UserController {
	
	private UsersService userService;
	private UserConverter userConverter;
	
	@org.springframework.beans.factory.annotation.Autowired
	public UserController(UsersService userService) {
		this.userService = userService;
		this.userConverter = new UserConverter();
	}
	//Create a new user
	@RequestMapping(
			path = "/iob/users",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public UserBoundary POSTUser (@RequestBody NewUserBoundary newUserBoundary) {
		
			return userService.createUser(userConverter.newUserbToUserb(newUserBoundary));
		}
	
	//Login valid user and retrieve user details
	@RequestMapping(
			path = "/iob/users/login/{userDomain}/{userEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
		public UserBoundary GETUser (@PathVariable("userDomain") String userDomain ,@PathVariable("userEmail") String userEmail ) {

			return userService.login(userDomain, userEmail);
		}
	
	
	//Update user details
	@RequestMapping(
			path = "/iob/users/{userDomain}/{userEmail}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public void PUTUser (@PathVariable("userDomain") String domain ,@PathVariable("userEmail") String userEmail , @RequestBody UserBoundary userBoundary) {
			
			userService.updateUser(domain, userEmail, userBoundary);
		}
}



