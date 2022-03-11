package demo;

import java.util.ArrayList;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bounderies.NewUserBoundary;
import bounderies.UserBoundary;
import bounderies.UserId;

@RestController
public class DemoController {
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
	
	
	@RequestMapping(
			path = "/iob/users",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public UserBoundary POSTUser (@RequestBody NewUserBoundary newUserBoundary) {
			
		UserId userId = new UserId()
				.setDomain("2022b.demo")
				.setEmail(newUserBoundary.getEmail());
		
		
		UserBoundary userBoundary = new UserBoundary()
				.setUserId(userId)
				.setRole(newUserBoundary.getRole())
				.setUsername(newUserBoundary.getUsername())
				.setAvatar(newUserBoundary.getAvatar());
		
		
		return userBoundary;
		}

	@RequestMapping(
			path = "/iob/admin/users",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
		public UserBoundary[] ExportAllUsers () {
		
		UserBoundary[] usersList = new UserBoundary[2];
		
		UserId userId1 = new UserId()
				.setDomain("2022b.demo")
				.setEmail("Bob@mail.com");
		
		UserBoundary userBoundary1 = new UserBoundary()
				.setUserId(userId1)
				.setRole("member")
				.setUsername("Bob")
				.setAvatar("1");
		
		UserId userId2 = new UserId()
				.setDomain("2022b.demo")
				.setEmail("Bob@mail.com");
		
		UserBoundary userBoundary2 = new UserBoundary()
				.setUserId(userId2)
				.setRole("member2")
				.setUsername("Alice")
				.setAvatar("2");
		
		
		usersList[0] = userBoundary1;
		usersList[1] = userBoundary2;
		
		return usersList;
		}

	@RequestMapping(
			path = "/iob/admin/activities",
			method = RequestMethod.DELETE)
	public void DeleteAllActivites () {
		
		}

	@RequestMapping(
			path = "/iob/admin/instances",
			method = RequestMethod.DELETE)
	public void DeleteAllInstances() {

		}
	
}
