package demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bounderies.InstanceBoundary;
import bounderies.InstanceId;
import bounderies.Location;
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
			path = "/iob/instances/{instanceDomain}/{instanceId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
		public InstanceBoundary GETInstance (@PathVariable("instanceDomain") String instanceDomain ,@PathVariable("instanceId") String instanceID ) {
			
			InstanceId instanceId = new InstanceId()
						.setDomain(instanceDomain)
						.setId(instanceID);
		
			Location location = new Location()
					.setLat(32.115139)
					.setLng(34.817804);
		
			Map<String, Object> map = new HashMap<>();
			map.put("key1", "can be set to any value you wish");
			map.put("key2", "you can also name the attributes any name you loke");
			map.put("key3", 4.2);
			map.put("key4", false);
				
		
			InstanceBoundary instanceBoundary = new InstanceBoundary()
					.setInstanceId(instanceId)
					.setType("dummyType")
					.setName("demo instance")
					.setActive(true)
					.setCreatedTimeStamp("2022-02-27T07:55:05.248+0000")
					.setLocation(location)
					.setInstanceAttributes(map);
			
			
			return instanceBoundary;
		}

	@RequestMapping(
			path = "/iob/instances",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
		public ArrayList<InstanceBoundary> GETAllInstances (@PathVariable("allInstances") ArrayList<InstanceBoundary> allInstances) {
		
			ArrayList<InstanceBoundary> instanceArr = new ArrayList<>();
			
		
			for (int i = 0; i < allInstances.size(); i++) {
				
				String currInstanceDomain = allInstances.get(i).getInstanceId().getDomain();
				String currInstanceId = allInstances.get(i).getInstanceId().getId();		
				
				instanceArr.add(GETInstance(currInstanceDomain,currInstanceId));
			}
			
			return instanceArr;
		
		}
}
