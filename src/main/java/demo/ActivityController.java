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
public class ActivityController {
	@RequestMapping( 
			path = "/iob/activities",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
		public InstanceBoundary postInstanceActivity (@RequestBody ActivityBoundary activityBoundary) {
			
			ActivityId activityId = new ActivityId()
					.setDomain("2022b.diana.ukrainsky")
					.setId("RandomID");
			
			//received ActivityBoundary Object and created new activityId for this object.
			activityBoundary.setActivityId(activityId);
			
			
			InstanceBoundary instanceBoundary = new InstanceBoundary().
					setInstanceId(activityBoundary.getInstance().getInstanceId());
			
			// return the InstanceBoundary the activity was used on.
			return instanceBoundary;
		}
}
