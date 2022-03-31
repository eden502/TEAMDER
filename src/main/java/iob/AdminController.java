package iob;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import iob.bounderies.ActivityBoundary;
import iob.bounderies.GeneralId;
import iob.bounderies.Instance;
import iob.bounderies.InvokedBy;
import iob.bounderies.UserBoundary;
import iob.bounderies.UserId;
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
			method = RequestMethod.DELETE
			)
		public void DELETEALLUser () {
			//delete all users
		userService.deleteAllUsers();
		}
	
	
	//Delete all activities in the domain
	@RequestMapping(
			path = "/iob/admin/activities",
			method = RequestMethod.DELETE)
	public void DELETEAllActivites () {
		//delete all activities
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
		
//		UserBoundary[] usersList = new UserBoundary[2];
//		
//		UserId userId1 = new UserId()
//				.setDomain("2022b.demo")
//				.setEmail("Bob@mail.com");
//		
//		UserBoundary userBoundary1 = new UserBoundary()
//				.setUserId(userId1)
//				.setRole("member")
//				.setUsername("Bob")
//				.setAvatar("1");
//		
//		UserId userId2 = new UserId()
//				.setDomain("2022b.demo")
//				.setEmail("Bob@mail.com");
//		
//		UserBoundary userBoundary2 = new UserBoundary()
//				.setUserId(userId2)
//				.setRole("member2")
//				.setUsername("Alice")
//				.setAvatar("2");
//
//		usersList[0] = userBoundary1;
//		usersList[1] = userBoundary2;

			return null;
		}
	
	//Export all activities
	@RequestMapping(
			path = "/iob/admin/activities",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
		public ActivityBoundary[] exportAllActivities () {
			List<ActivityBoundary> allActivities = activitiesService.getAllActivities();
			
//		GeneralId activityId = new GeneralId()
//					.setDomain("2022b.demo")
//					.setId("950");
//			
//			Instance instance = new Instance()
//					.setInstanceId( new GeneralId()
//										.setDomain("2022b.demo")
//										.setId("42"));
//			
//			UserId userId = new UserId()
//					.setDomain("2022b.demo")
//					.setEmail("keren1997rachev@gmail.com");
//			
//			InvokedBy invokeBy = new InvokedBy().setUserId(userId);
//			
//			Map<String, Object> activityAttributes = new HashMap<String, Object>() ;
//			Map<String, Object> nestedAttribute = new HashMap<String, Object>() ;
//			
//			activityAttributes.put("key1", "Keren :) ");
//			nestedAttribute.put("key2key1", " This is a nexted JSON!");
//			activityAttributes.put("key2", nestedAttribute);
//			
//			
//			ActivityBoundary activityBounday1 = new ActivityBoundary()
//					.setActivityId(activityId)
//					.setType("demoActivityType")
//					.setInstance(instance)
//					.setCreatedTimestamp(java.time.LocalDateTime.now().toString())
//					.setInvokedBy(invokeBy)
//					.setActivityAttributes(activityAttributes);
//			
//			ActivityBoundary activityBounday2 = new ActivityBoundary()
//					.setActivityId(activityId)
//					.setType("demoActivityType")
//					.setInstance(instance)
//					.setCreatedTimestamp(java.time.LocalDateTime.now().toString())
//					.setInvokedBy(invokeBy)
//					.setActivityAttributes(activityAttributes);
//			
//			ActivityBoundary activityBounday3 = new ActivityBoundary()
//					.setActivityId(activityId)
//					.setType("demoActivityType")
//					.setInstance(instance)
//					.setCreatedTimestamp(java.time.LocalDateTime.now().toString())
//					.setInvokedBy(invokeBy)
//					.setActivityAttributes(activityAttributes);
//			
//			ActivityBoundary [] activityBoundaryArray  = new ActivityBoundary[3];
//			activityBoundaryArray[0] = activityBounday1;
//			activityBoundaryArray[1] = activityBounday2;
//			activityBoundaryArray[2] = activityBounday3;
			return  allActivities.toArray(new ActivityBoundary[0]);
		}
}
