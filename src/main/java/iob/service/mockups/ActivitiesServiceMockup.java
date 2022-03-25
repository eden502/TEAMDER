package iob.service.mockups;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Vector;
import org.springframework.beans.factory.annotation.Autowired;
import bounderies.ActivityBoundary;
import iob.data.ActivityEntity;
import iob.logic.ActivitiesService;

@Service
public class ActivitiesServiceMockup implements ActivitiesService{

	Vector<ActivityEntity> v = new Vector<ActivityEntity>();
	
	@Autowired
	public ActivitiesServiceMockup() {
		
	}
	@Override
	public Object invokeActivity(ActivityBoundary activity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityBoundary> getAllActivities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllAcitivities() {
		// TODO Auto-generated method stub
		
	}

}
