package iob.logic;

import java.util.List;

import iob.bounderies.ActivityBoundary;

public interface ActivitiesServiceEnhanced extends ActivitiesService {
	public void deleteAllAcitivities(String domain, String email);
	public List<ActivityBoundary> getAllActivities(String userDomain, String userEmail, int size, int page);
}
