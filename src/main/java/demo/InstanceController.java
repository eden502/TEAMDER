package demo;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import bounderies.Location;

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
public class InstanceController {
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
		public void PUTInstance (@PathVariable("instanceDomain") String domain ,@PathVariable("instanceId") String id,@RequestBody InstanceBoundary ib) {
		
		
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
					.setCreatedTimeStamp(java.time.LocalDateTime.now().toString())
					.setLocation(location)
					.setInstanceAttributes(map);
			
			
			return instanceBoundary;


		}

	@RequestMapping(

			path = "/iob/instances",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
		public InstanceBoundary[] GETAllInstances () {
		
			InstanceBoundary[] instanceArr = new InstanceBoundary[2];

			Map<String, Object> map = new HashMap<>();
			map.put("key1", "can be set to any value you wish");
			map.put("key2", "you can also name the attributes any name you loke");
			map.put("key3", 4.2);
			map.put("key4", false);
			
			InstanceId instanceId0 = new InstanceId()
					.setDomain("2022b.demo0")
					.setId("40");
		
			Location location0 = new Location()
					.setLat(32.015139)
					.setLng(34.017804);
		
			
			InstanceBoundary instanceBoundary0 = new InstanceBoundary()
					.setInstanceId(instanceId0)
					.setType("dummyType")
					.setName("demo instance")
					.setActive(true)
					.setCreatedTimeStamp(java.time.LocalDateTime.now().toString())
					.setLocation(location0)
					.setInstanceAttributes(map);
			
			InstanceId instanceId1 = new InstanceId()
					.setDomain("2022b.demo1")
					.setId("41");
		
			Location location1 = new Location()
					.setLat(32.115139)
					.setLng(34.117804);
		
			
			InstanceBoundary instanceBoundary1 = new InstanceBoundary()
					.setInstanceId(instanceId1)
					.setType("dummyType")
					.setName("demo instance")
					.setActive(true)
					.setCreatedTimeStamp(java.time.LocalDateTime.now().toString())
					.setLocation(location1)
					.setInstanceAttributes(map);
			
			instanceArr[0] = instanceBoundary0;
			instanceArr[1] = instanceBoundary1;
			
			return instanceArr;
		
		}
}
