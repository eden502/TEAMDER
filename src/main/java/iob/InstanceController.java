package iob;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import iob.bounderies.CreatedBy;
import iob.bounderies.GeneralId;
import iob.bounderies.InstanceBoundary;
import iob.bounderies.Location;
import iob.bounderies.UserId;
import iob.logic.InstancesService;

 
@RestController
public class InstanceController {
	// private long idGenerator =  0;   --> Please implement inside the service!
	private InstancesService instancesService;
	
	@org.springframework.beans.factory.annotation.Autowired
	public InstanceController(InstancesService instancesService) {
		this.instancesService = instancesService;
	}
	//Create an instance
	@RequestMapping(
			path = "/iob/instances",		
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public InstanceBoundary POSTInstance(@RequestBody InstanceBoundary ib) {
			GeneralId instanceId = new GeneralId();
			instanceId.setDomain("2022b.diana.ukrainsky");
			instanceId.setId("" + (0));
			
			InstanceBoundary instanceBoundary = new InstanceBoundary();
			instanceBoundary.setInstanceId(instanceId);
			instanceBoundary.setType(ib.getType());
			instanceBoundary.setName(ib.getName());
			instanceBoundary.setActive(ib.getActive());
			instanceBoundary.setCreatedTimestamp(ib.getCreatedTimestamp());
			instanceBoundary.setCreatedBy(ib.getCreatedBy());
			instanceBoundary.setLocation(ib.getLocation());
			instanceBoundary.setInstanceAttributes(ib.getInstanceAttributes());
			
			return instanceBoundary;
			
		}
	
	//Update an instance
	@RequestMapping(
			path = "/iob/instances/{instanceDomain}/{instanceId}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
		public void PUTInstance (@PathVariable("instanceDomain") String domain ,@PathVariable("instanceId") String id,@RequestBody InstanceBoundary ib) {
		
		//Update instance fields.
		
	}
	
	
	//Retrieve instance
	@RequestMapping( 
			path = "/iob/instances/{instanceDomain}/{instanceId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
		public InstanceBoundary GETInstance (@PathVariable("instanceDomain") String instanceDomain ,@PathVariable("instanceId") String instanceID ) {		
			return instancesService.getSpecificInstance(instanceDomain, instanceID);
		}

	//Get all instances
	@RequestMapping(

			path = "/iob/instances",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
		public InstanceBoundary[] GETAllInstances () {
		
			return instancesService.getAllInstances().toArray(new InstanceBoundary[0]);
		
		}
}
