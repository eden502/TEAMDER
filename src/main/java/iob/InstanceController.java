package iob;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iob.bounderies.InstanceBoundary;
import iob.logic.InstanceServiceEnhanced;
import iob.logic.InstancesService;

@RestController
public class InstanceController {
	private InstanceServiceEnhanced instancesService;

	@org.springframework.beans.factory.annotation.Autowired
	public InstanceController(InstanceServiceEnhanced instancesService) {
		this.instancesService = instancesService;
	}

	// Create an instance
	@RequestMapping(path = "/iob/instances", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary createInstance(@RequestBody InstanceBoundary ib) {
		return this.instancesService.createInstance(ib);
	}

	// Update an instance - last update 30/04/22 (Sprint 5)
	@RequestMapping(path = "/iob/instances/{instanceDomain}/{instanceId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateInstance(@PathVariable("instanceDomain") String instanceDomain, @PathVariable("instanceId") String instanceId, @RequestParam(name="userDomain", required = true) String userDomain,
			@RequestParam(name="userEmail", required = true) String userEmail,
			@RequestBody InstanceBoundary ib) {
		this.instancesService.updateInstance(userDomain,userEmail,instanceDomain, instanceId, ib);
	}
	
	// Search instances by distance - new method for Spring 5 using Geo Location of MongoDB
		@RequestMapping(path = "/iob/instances/search/near/{lat}/{lng}/{distance}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public InstanceBoundary[] searchInstanceByLocation(@PathVariable("lat") double lat,
															@PathVariable("lng") double lng,
															@PathVariable("distance") double distance,
															@RequestParam(name="userDomain", required = true) String userDomain,
															@RequestParam(name="userEmail", required = true) String userEmail,
															@RequestParam(name="size", required = true) String size,
															@RequestParam(name="page", required = true) String page) {
			
			return this.instancesService.getInstancesNear(userDomain,userEmail,page,size,lat,lng,distance).toArray(new InstanceBoundary[0]);

		}

	// Retrieve instance
	@RequestMapping(path = "/iob/instances/{instanceDomain}/{instanceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary getInstance(@PathVariable("instanceDomain") String instanceDomain,
			@PathVariable("instanceId") String instanceID) {
		return instancesService.getSpecificInstance(instanceDomain, instanceID);
	}

	// Get all instances
	@RequestMapping(

			path = "/iob/instances", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary[] getAllInstances() {

		return instancesService.getAllInstances().toArray(new InstanceBoundary[0]);

	}
}
