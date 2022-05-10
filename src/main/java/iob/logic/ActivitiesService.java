package iob.logic;

import java.util.List;

import iob.bounderies.ActivityBoundary;

public interface ActivitiesService {

	public Object invokeActivity(ActivityBoundary activity);
	@Deprecated
	public List<ActivityBoundary> getAllActivities();
	@Deprecated
	public void deleteAllAcitivities();
}
