package iob.service.mockups;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import iob.bounderies.ActivityBoundary;
import iob.bounderies.GeneralId;
import iob.data.ActivityEntity;
import iob.logic.ActivitiesService;
import iob.logic.ActivityConverter;

@Service
public class ActivitiesServiceMockup implements ActivitiesService{

	Vector<ActivityEntity> activitiesEntityVector;
	private AtomicLong idGenerator; 
	private ActivityConverter activityConverter;
	
	@Autowired
	public ActivitiesServiceMockup() {
		activitiesEntityVector = new Vector<>();
		idGenerator = new AtomicLong();
		activityConverter = new ActivityConverter();
	}
	@Override
	public Object invokeActivity(ActivityBoundary activity) {
		validateActivityBoundary(activity);
		
		GeneralId activityId = new GeneralId();
		activityId.setDomain("2022b.diana.ukrainsky");
		activityId.setId("" + idGenerator.incrementAndGet());
		
		activity.setActivityId(activityId);
		activity .setCreatedTimestamp(java.time.LocalDateTime.now().toString());
		
		activitiesEntityVector.add(activityConverter.toEntity(activity));
		return activity;
	}

	
	@Override
	public List<ActivityBoundary> getAllActivities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllAcitivities() {
		this.activitiesEntityVector.clear();
	}
	
	private void validateActivityBoundary(ActivityBoundary activity) {
		if(activity.getActivityId() != null)
			throw new RuntimeException("ActivityBoundary must have null ActivityId");
		
		if(activity.getInstance() == null)
			throw new RuntimeException("ActivityBoundary must have a valid Instance");
		
		if(activity.getInstance().getInstanceId() == null)
			throw new RuntimeException("ActivityBoundary`s Instance must have a valid InstanceId");
		
		if(activity.getInstance().getInstanceId().getDomain() == null||
				activity.getInstance().getInstanceId().getDomain().trim().isEmpty())
			throw new RuntimeException("ActivityBoundary`s InstanceId must have a valid domain");
		
		if(!activity.getInstance().getInstanceId().getDomain().equals("2022b.diana.ukrainsky"))
			throw new RuntimeException("ActivityBoundary`s InstanceId Wrong instance domain");
		
		if(activity.getInstance().getInstanceId().getId() == null||
				activity.getInstance().getInstanceId().getId().trim().isEmpty())
			throw new RuntimeException("ActivityBoundary`s InstanceId must have a valid id");
		
		if(activity.getInvokedBy() == null)
			throw new RuntimeException("ActivityBoundary must have a valid InvokedBy");
		
		if(activity.getInvokedBy().getUserId() == null)
			throw new RuntimeException("ActivityBoundary`s InvokedBy must have a valid UserId");
		
		if(activity.getInvokedBy().getUserId().getDomain() == null||
				activity.getInvokedBy().getUserId().getDomain().trim().isEmpty())
			throw new RuntimeException("ActivityBoundary`s InvokedBy must have a valid domain");
		
		if(!activity.getInvokedBy().getUserId().getDomain().equals("2022b.diana.ukrainsky"))
			throw new RuntimeException("ActivityBoundary`s InvokedBy Wrong user domain");
		
		if(activity.getInvokedBy().getUserId().getEmail() == null||
				activity.getInvokedBy().getUserId().getEmail().trim().isEmpty())
			throw new RuntimeException("ActivityBoundary`s InvokedBy must have a valid id");
		
	}

}
