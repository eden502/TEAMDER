package iob;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import iob.bounderies.ActivityBoundary;
import iob.bounderies.UserBoundary;
import iob.logic.ActivitiesService;
import iob.logic.InstancesService;
import iob.logic.UsersService;
 
@RestController
public class AdminController {
	
	private UsersService userService;
	private InstancesService instancesService;
	private ActivitiesService activitiesService;
	
	@Autowired
	public AdminController(UsersService usersService,InstancesService instancesService,ActivitiesService activitiesService) {
		this.userService = usersService;
		this.instancesService = instancesService;
		this.activitiesService = activitiesService;
	}
	//Delete all users in the domain
	@RequestMapping(
			path = "/iob/admin/users",
			method = RequestMethod.DELETE)
	
		public void DELETEALLUser () {
			userService.deleteAllUsers();
		}
	
	
	//Delete all activities in the domain
	@RequestMapping(
			path = "/iob/admin/activities",
			method = RequestMethod.DELETE)
	
	public void DELETEAllActivites () {
		activitiesService.deleteAllAcitivities();
		}

	
	//Delete all instances in the domain
	@RequestMapping(
			path = "/iob/admin/instances",
			method = RequestMethod.DELETE)
	
	public void DELETEAllInstances() {
			instancesService.deleteAllInstances();
		}
	
	
	//Export all users
	@RequestMapping(
			path = "/iob/admin/users",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
		public UserBoundary[] ExportAllUsers () {
		return userService.getAllUsers().toArray(new UserBoundary[0]);
		}
	
	//Export all activities
	@RequestMapping(
			path = "/iob/admin/activities",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
		public ActivityBoundary[] exportAllActivities () {
			return  activitiesService.getAllActivities().toArray(new ActivityBoundary[0]);
		}
}
