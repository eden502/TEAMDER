package iob;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import iob.bounderies.InstanceBoundary;
import iob.logic.InstancesService;

@RestController
public class InstanceController {
	private InstancesService instancesService;

	@org.springframework.beans.factory.annotation.Autowired
	public InstanceController(InstancesService instancesService) {
		this.instancesService = instancesService;
	}

	// Create an instance
	@RequestMapping(path = "/iob/instances", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary createInstance(@RequestBody InstanceBoundary ib) {
		return this.instancesService.createInstance(ib);
	}

	// Update an instance
	@RequestMapping(path = "/iob/instances/{instanceDomain}/{instanceId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateInstance(@PathVariable("instanceDomain") String domain, @PathVariable("instanceId") String id,
			@RequestBody InstanceBoundary ib) {
		this.instancesService.updateInstance(domain, id, ib);
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
