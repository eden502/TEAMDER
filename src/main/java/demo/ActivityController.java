package demo;



import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import bounderies.ActivityBoundary;
import bounderies.ActivityId;
import bounderies.InstanceBoundary;

 
@RestController
public class ActivityController {
	private long idGenerator =  0;
	
	//Invoke an instance activity
	@RequestMapping( 
			path = "/iob/activities",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public InstanceBoundary postInstanceActivity (@RequestBody ActivityBoundary activityBoundary) {
			
			ActivityId activityId = new ActivityId()
					.setDomain("2022b.diana.ukrainsky")
					.setId("" + (++idGenerator));
			
			//received ActivityBoundary Object and created new activityId for this object.
			activityBoundary.setActivityId(activityId);
			
			
			InstanceBoundary instanceBoundary = new InstanceBoundary().
					setInstanceId(activityBoundary.getInstance().getInstanceId());
			
			// return the InstanceBoundary the activity was used on.
			return instanceBoundary;
		}
}
