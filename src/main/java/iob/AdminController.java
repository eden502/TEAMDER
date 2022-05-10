package iob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import iob.bounderies.ActivityBoundary;
import iob.bounderies.UserBoundary;
import iob.logic.ActivitiesService;
import iob.logic.ActivitiesServiceEnhanced;
import iob.logic.InstanceServiceEnhanced;
import iob.logic.InstancesService;
import iob.logic.UsersService;
import iob.logic.UsersServiceEnhanced;

@RestController
public class AdminController {

	private UsersServiceEnhanced userService;
	private InstanceServiceEnhanced instancesService;
	private ActivitiesServiceEnhanced activitiesService;

	@Autowired
	public AdminController(UsersServiceEnhanced usersService, InstanceServiceEnhanced instancesService,
			ActivitiesServiceEnhanced activitiesService) {
		this.userService = usersService;
		this.instancesService = instancesService;
		this.activitiesService = activitiesService;
	}

	// Delete all users in the domain
	//@RequestMapping(path = "/iob/admin/users",
	//method = RequestMethod.DELETE)
	@Deprecated
	public void deleteAllUsers() {
		//userService.deleteAllUsers();
		throw new RuntimeException("Deprecated Method");
	}
	@RequestMapping(path = "/iob/admin/users",
			method = RequestMethod.DELETE)
	public void deleteAllUsers(
			@RequestParam(name="userDomain",required = true) String userDomain,
			@RequestParam(name = "userEmail",required = true) String userEmail) {
		userService.deleteAllUsers(userDomain, userEmail);
	}

	// Delete all activities in the domain
	@RequestMapping(path = "/iob/admin/activities", method = RequestMethod.DELETE)

	public void deleteAllActivites(
			@RequestParam(name="userDomain",required = true) String userDomain,
			@RequestParam(name = "userEmail",required = true) String userEmail) {
		activitiesService.deleteAllAcitivities(userDomain,userEmail);
	}

	// Delete all instances in the domain
	@Deprecated
	public void deleteAllInstances() {
		//instancesService.deleteAllInstances();
		throw new RuntimeException("Deprecated Method");
	}
	@RequestMapping(
			path = "/iob/admin/instances",
			method = RequestMethod.DELETE)
	public void deleteAllInstances(
			@RequestParam(name="userDomain",required = true) String userDomain,
			@RequestParam(name = "userEmail",required = true) String userEmail) {
		instancesService.deleteAllInstances(userDomain, userEmail);
	}
	
	
	
	// Export all users
	@RequestMapping(
			path = "/iob/admin/users",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] exportAllUsers(
			@RequestParam(name="userDomain",required = true) String userDomain,
			@RequestParam(name = "userEmail",required = true) String userEmail,
			@RequestParam(name="size", required = false, defaultValue = "10") int size,
			@RequestParam(name="page", required = false, defaultValue = "0") int page) {
		return userService.getAllUsers(userDomain,userEmail,size,page).toArray(new UserBoundary[0]);
	}
	

	// Export all activities
	@RequestMapping(path = "/iob/admin/activities",
					method = RequestMethod.GET,
					produces = MediaType.APPLICATION_JSON_VALUE)
	
	public ActivityBoundary[] exportAllActivities(
			@RequestParam(name="userDomain",required = true) String userDomain,
			@RequestParam(name = "userEmail",required = true) String userEmail,
			@RequestParam(name="size", required = false, defaultValue = "10") int size,
			@RequestParam(name="page", required = false, defaultValue = "0") int page) {
		return activitiesService.getAllActivities(userDomain, userEmail, size, page).toArray(new ActivityBoundary[0]);
	}
	
//	@Deprecated
//	public ActivityBoundary[] exportAllActivities() {
//		return activitiesService.getAllActivities().toArray(new ActivityBoundary[0]);
//	}
}
