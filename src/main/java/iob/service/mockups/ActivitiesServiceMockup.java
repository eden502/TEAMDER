package iob.service.mockups;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import iob.bounderies.ActivityBoundary;
import iob.bounderies.GeneralId;
import iob.data.ActivityEntity;
import iob.logic.ActivitiesService;
import iob.logic.ActivityConverter;

@Service
public class ActivitiesServiceMockup implements ActivitiesService {

	private List<ActivityEntity> activitiesEntityList;
	private AtomicLong idGenerator;
	private ActivityConverter activityConverter;
	private String domain;

	@Autowired
	public ActivitiesServiceMockup(ActivityConverter activityConverter) {
		idGenerator = new AtomicLong();
		this.activityConverter = activityConverter;
	}

	@Value("${spring.application.name:null}")
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@PostConstruct
	public void init() {
		// create a thread safe list
		this.activitiesEntityList = Collections.synchronizedList(new ArrayList<>());
	}

	@Override
	public Object invokeActivity(ActivityBoundary activity) {
		validateActivityBoundary(activity);

		GeneralId activityId = new GeneralId();
		activityId.setDomain(this.domain);
		activityId.setId("" + idGenerator.incrementAndGet());

		activity.setActivityId(activityId);
		activity.setCreatedTimestamp(java.time.LocalDateTime.now().toString());

		activitiesEntityList.add(activityConverter.toEntity(activity));
		return activity;
	}

	@Override
	public List<ActivityBoundary> getAllActivities() {
		return activitiesEntityList // Vector<ActivityEntity>
				.stream() // Stream<ActivityEntity>
				.map(activityConverter::toBoundary) // Stream<ActivityBoundary>
				.collect(Collectors.toList()); // List<ActivityBoundary>

	}

	@Override
	public void deleteAllAcitivities() {
		this.activitiesEntityList.clear();
	}

	private void validateActivityBoundary(ActivityBoundary activity) {
		if (activity.getActivityId() != null)
			throw new RuntimeException("ActivityBoundary must have null ActivityId");

		if (activity.getInstance() == null)
			throw new RuntimeException("ActivityBoundary must have a valid Instance");

		if (activity.getInstance().getInstanceId() == null)
			throw new RuntimeException("ActivityBoundary`s Instance must have a valid InstanceId");

		if (activity.getInstance().getInstanceId().getDomain() == null
				|| activity.getInstance().getInstanceId().getDomain().trim().isEmpty())
			throw new RuntimeException("ActivityBoundary`s InstanceId must have a valid domain");

		if (!activity.getInstance().getInstanceId().getDomain().equals("2022b.diana.ukrainsky"))
			throw new RuntimeException("ActivityBoundary`s InstanceId Wrong instance domain");

		if (activity.getInstance().getInstanceId().getId() == null
				|| activity.getInstance().getInstanceId().getId().trim().isEmpty())
			throw new RuntimeException("ActivityBoundary`s InstanceId must have a valid id");

		if (activity.getInvokedBy() == null)
			throw new RuntimeException("ActivityBoundary must have a valid InvokedBy");

		if (activity.getInvokedBy().getUserId() == null)
			throw new RuntimeException("ActivityBoundary`s InvokedBy must have a valid UserId");

		if (activity.getInvokedBy().getUserId().getDomain() == null
				|| activity.getInvokedBy().getUserId().getDomain().trim().isEmpty())
			throw new RuntimeException("ActivityBoundary`s InvokedBy must have a valid domain");

		if (!activity.getInvokedBy().getUserId().getDomain().equals("2022b.diana.ukrainsky"))
			throw new RuntimeException("ActivityBoundary`s InvokedBy Wrong user domain");

		if (activity.getInvokedBy().getUserId().getEmail() == null
				|| activity.getInvokedBy().getUserId().getEmail().trim().isEmpty())
			throw new RuntimeException("ActivityBoundary`s InvokedBy must have a valid id");

	}

}
