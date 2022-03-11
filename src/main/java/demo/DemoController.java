package demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bounderies.ActivityBoundary;
import bounderies.ActivityId;
import bounderies.Instance;
import bounderies.InstanceBoundary;
import bounderies.InstanceId;
import bounderies.InvokedBy;
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
			path = "/iob/instances",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public InstanceBoundary POSTInstance(@RequestBody InstanceBoundary ib) {
			InstanceId instanceId = new InstanceId()
					.setDomain("2022b.demo")
					.setId("42");
			
			InstanceBoundary instanceBoundary = new InstanceBoundary()
					.setInstanceId(instanceId)
					.setType(ib.getType())
					.setName(ib.getName())
					.setActive(ib.getActive())
					.setCreatedTimeStamp(ib.getCreatedTimeStamp())
					.setCreatedBy(ib.getCreatedBy())
					.setLocation(ib.getLocation())
					.setInstanceAttributes(ib.getInstanceAttributes());
			
			return instanceBoundary;
			
		}
	
	@RequestMapping(
			path = "/iob/instances/{instanceDomain}/{instanceId}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
		public String PUTInstance (@PathVariable("instanceDomain") String domain ,@PathVariable("instanceId") String id ) {
		
		
			return "Searching for Domain:"+domain+"\nId: "+id;
		
	}
	@RequestMapping(
			path = "/iob/activities",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public UserBoundary postInstanceActivity (@RequestBody ActivityBoundary newUserBoundary) {
			
			UserId userId = new UserId()
					.setDomain("2022b.demo")
					.setEmail("keren1997rachev@gmail.com");
			
			
			UserBoundary userBoundary = new UserBoundary()
					.setUserId(userId)
					.setRole("Manager")
					.setUsername("Demo User")
					.setAvatar("J");
			
			
			return userBoundary;
		}
	
	
	@RequestMapping(
			path = "/iob/admin/activities",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
		public ActivityBoundary[] exportAllActivities () {
			
			ActivityId activityId = new ActivityId()
					.setDomain("2022b.demo")
					.setId("950");
			
			Instance instance = new Instance()
					.setInstanceId( new InstanceId()
										.setDomain("2022b.demo")
										.setId("42"));
			
			UserId userId = new UserId()
					.setDomain("2022b.demo")
					.setEmail("keren1997rachev@gmail.com");
			
			InvokedBy invokeBy = new InvokedBy().setUserId(userId);
			
			Map<String, Object> activityAttributes = new HashMap<String, Object>() ;
			Map<String, Object> nestedAttribute = new HashMap<String, Object>() ;
			
			activityAttributes.put("key1", "Keren :) ");
			nestedAttribute.put("key2key1", " This is a nexted JSON!");
			activityAttributes.put("key2", nestedAttribute);
			
			
			ActivityBoundary activityBounday1 = new ActivityBoundary()
					.setActivityId(activityId)
					.setType("demoActivityType")
					.setInstance(instance)
					.setCreatedTimestamp("11-3-2022")
					.setInvokeId(invokeBy)
					.setActivityAttributes(activityAttributes);
			
			ActivityBoundary activityBounday2 = new ActivityBoundary()
					.setActivityId(activityId)
					.setType("demoActivityType")
					.setInstance(instance)
					.setCreatedTimestamp("11-3-2022")
					.setInvokeId(invokeBy)
					.setActivityAttributes(activityAttributes);
			
			ActivityBoundary activityBounday3 = new ActivityBoundary()
					.setActivityId(activityId)
					.setType("demoActivityType")
					.setInstance(instance)
					.setCreatedTimestamp("11-3-2022")
					.setInvokeId(invokeBy)
					.setActivityAttributes(activityAttributes);
			
			ActivityBoundary [] activityBoundaryArray  = new ActivityBoundary[3];
			activityBoundaryArray[0] = activityBounday1;
			activityBoundaryArray[1] = activityBounday2;
			activityBoundaryArray[2] = activityBounday3;
			
			return activityBoundaryArray;
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
	public void DELETEAllActivites () {
		//delete all activities
		}

	@RequestMapping(
			path = "/iob/admin/instances",
			method = RequestMethod.DELETE)
	public void DELETEAllInstances() {
		//delete all instances
		}
	
}
