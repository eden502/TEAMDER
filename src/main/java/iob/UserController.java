package iob;



import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import bounderies.NewUserBoundary;
import bounderies.UserBoundary;
import bounderies.UserId;
import iob.logic.UsersService;
 
@RestController
public class UserController {
	
	private UsersService userService;
	
	@org.springframework.beans.factory.annotation.Autowired
	public UserController(UsersService userService) {
		this.userService = userService;
	}
	//Create a new user
	@RequestMapping(
			path = "/iob/users",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public UserBoundary POSTUser (@RequestBody NewUserBoundary newUserBoundary) {
			
			UserId userId = new UserId()
					.setDomain("2022b.diana.ukrainsky")
					.setEmail(newUserBoundary.getEmail());
			
			UserBoundary userBoundary = new UserBoundary()
					.setUserId(userId)
					.setRole(newUserBoundary.getRole())
					.setUsername(newUserBoundary.getUsername())
					.setAvatar(newUserBoundary.getAvatar());
			
			
			return userBoundary;
		}
	
	//Login valid user and retrieve user details
	@RequestMapping(
			path = "/iob/users/login/{userDomain}/{userEmail}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
		public UserBoundary GETUser (@PathVariable("userDomain") String userDomain ,@PathVariable("userEmail") String userEmail ) {
			
			UserId userId = new UserId()
					.setDomain(userDomain)
					.setEmail(userEmail);
			
			
			UserBoundary userBoundary = new UserBoundary()
					.setUserId(userId)
					.setRole("Manager")
					.setUsername("Demo User")
					.setAvatar("J");
			
			
			return userBoundary;
		}
	
	
	//Update user details
	@RequestMapping(
			path = "/iob/users/{userDomain}/{userEmail}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public void PUTUser (@PathVariable("userDomain") String domain ,@PathVariable("userEmail") String userEmail , @RequestBody UserBoundary userBoundary) {
			

			//user to Update  - will be retrieved from the database 
			UserBoundary userBoundaryToUpdate  = new UserBoundary()
					.setUserId(new UserId().setDomain(domain).setEmail(userEmail));
			
			// check if path variables are valid if they are than update the relevant userBoundary.
			if(userBoundaryToUpdate.getUserId().getDomain().equals(domain) && 
					userBoundaryToUpdate.getUserId().getEmail().equals(userEmail)){
						userBoundaryToUpdate
							.setAvatar(userBoundary.getAvatar())
							.setUsername(userBoundary.getUsername())
							.setRole(userBoundary.getRole());
					}
		
		}
}
