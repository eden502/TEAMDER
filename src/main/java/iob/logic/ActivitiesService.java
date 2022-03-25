package iob.logic;

import java.util.List;

import bounderies.ActivityBoundary;

public interface ActivitiesService {

	public Object invokeActivity(ActivityBoundary activity);
	public List<ActivityBoundary> getAllActivities();
	public void deleteAllAcitivities();
}
