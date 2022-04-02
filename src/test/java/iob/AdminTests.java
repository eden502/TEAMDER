package iob;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import iob.bounderies.ActivityBoundary;
import iob.bounderies.GeneralId;
import iob.bounderies.Instance;
import iob.bounderies.InstanceBoundary;
import iob.bounderies.InvokedBy;
import iob.bounderies.UserBoundary;
import iob.bounderies.UserId;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminTests {
	private int port;
	private String activitiesUrl;
	private String usersUrl;
	private String instancesUrl;
	private String getInstancesUrl;
	private RestTemplate restTemplate;
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}
	
	@PostConstruct
	public void testsInit() {
		this.activitiesUrl = "http://localhost:" + this.port + "/iob/admin/activities";
		this.usersUrl = "http://localhost:" + this.port + "/iob/admin/users";
		this.instancesUrl = "http://localhost:" + this.port + "/iob/admin/instances";
		this.getInstancesUrl = "http://localhost:" + this.port + "/iob/instances";
		this.restTemplate = new RestTemplate();
	}
	

	@AfterEach
	public void tearDown() {
		this.restTemplate
			.delete(this.activitiesUrl);
		
		this.restTemplate
		.delete(this.usersUrl);
		
		this.restTemplate
		.delete(this.instancesUrl);
	}
	
	
	
	@Test
	public void testDeleteAllUsersActuallyDeletesAllUsers() throws Exception {
		// GIVEN the server is up
		// AND the server contains 3 Users
		// TODO initialize 3 newUserBoundary
		
		//TODO initialize newUserBoundary 1

		
		
		//TODO initialize newUserBoundary 2

		
		//TODO initialize newUserBoundary 3
		
		
		
		//TODO HTTP POST newUserBoundary 1

		
		//TODO HTTP POST newUserBoundary 2

		
		//TODO HTTP POST newUserBoundary 3

		
		
		// WHEN I DELETE all users
		

		
		//TODO HTTP GET All users

		
		
		// THEN the server returns status 2xx
		// AND there are no users in the domain
		// TODO check users array retrieved is empty


	}
	
	
	@Test
	public void testDeleteAllInstancesActuallyDeletesAllInstances() throws Exception {
		// GIVEN the server is up
		// AND the server contains 3 Instances
		// TODO initialize 3 InstanceBoundary
		
		//TODO initialize InstanceBoundary 1

		
		
		//TODO initialize InstanceBoundary 2

		
		//TODO initialize InstanceBoundary 3
		
		
		
		//TODO HTTP POST InstanceBoundary 1

		
		//TODO HTTP POST InstanceBoundary 2

		
		//TODO HTTP POST InstanceBoundary 3

		
		
		// WHEN I DELETE all Instances
		

		
		//TODO HTTP GET All Instances

		
		
		// THEN the server returns status 2xx
		// AND there are no Instances in the domain
		// TODO check Instances array retrieved is empty


	}
	
	
	@Test
	public void testDeleteAllActivitiesActuallyDeletesAllActivities() throws Exception {
		// GIVEN the server is up
		// AND the server contains 3 Activities
		// TODO initialize 3 ActivityBoundary
		
		//TODO initialize ActivityBoundary 1

		
		
		//TODO initialize ActivityBoundary 2

		
		//TODO initialize ActivityBoundary 3
		
		
		
		//TODO HTTP POST ActivityBoundary 1

		
		//TODO HTTP POST ActivityBoundary 2

		
		//TODO HTTP POST ActivityBoundary 3

		
		
		// WHEN I DELETE all Activities
		

		
		//TODO HTTP GET All Activities

		
		
		// THEN the server returns status 2xx
		// AND there are no Activities in the domain
		// TODO check Activities array retrieved is empty

	}
	
	@Test
	public void testExportAllActivitiesActuallyExportsAllActivities() throws Exception {
		// GIVEN the server is up
		// AND the server contains 3 Activities
		// initialize 3 ActivityBoundary
		
		ActivityBoundary activityBoundary1 = new ActivityBoundary();
		ActivityBoundary activityBoundary2 = new ActivityBoundary();
		ActivityBoundary activityBoundary3 = new ActivityBoundary();
		
		// initialize ActivityBoundary 1
		GeneralId activityGeneralId_1 = new GeneralId();
		activityGeneralId_1.setDomain("2022b.diana.ukrainsky");
		activityGeneralId_1.setId("1");
		
		GeneralId instanceGeneralId_1 = new GeneralId();
		instanceGeneralId_1.setDomain("2022b.diana.ukrainsky");
		instanceGeneralId_1.setId("12");
		
		Instance instance_1 = new Instance();
		instance_1.setInstanceId(instanceGeneralId_1);
		
		UserId userId_1 = new UserId();
		userId_1.setDomain("2022b.diana.ukrainsky");
		userId_1.setEmail("user1@gmail.com");
		
		InvokedBy invokedBy1 = new InvokedBy();
		invokedBy1.setUserId(userId_1);
		
		Map<String,Object> activityAttributes1 = new HashMap<>();
		activityAttributes1.put("Key1", 100);
		activityAttributes1.put("Key2", "Test");
		activityAttributes1.put("Key3", 55.55);
		
		
		// set ActivityBoundary 1
		activityBoundary1.setActivityId(activityGeneralId_1);
		activityBoundary1.setType("adminTestsType");
		activityBoundary1.setInstance(instance_1);
		activityBoundary1.setCreatedTimestamp(java.time.LocalDateTime.now().toString());
		activityBoundary1.setInvokedBy(invokedBy1);
		activityBoundary1.setActivityAttributes(activityAttributes1);
						
		// initialize ActivityBoundary 2
		GeneralId activityGeneralId_2 = new GeneralId();
		activityGeneralId_2.setDomain("2022b.diana.ukrainsky");
		activityGeneralId_2.setId("2");
		
		GeneralId instanceGeneralId_2 = new GeneralId();
		instanceGeneralId_2.setDomain("2022b.diana.ukrainsky");
		instanceGeneralId_2.setId("23");
		
		Instance instance_2 = new Instance();
		instance_2.setInstanceId(instanceGeneralId_2);
		
		UserId userId_2 = new UserId();
		userId_2.setDomain("2022b.diana.ukrainsky");
		userId_2.setEmail("user2@gmail.com");
		
		InvokedBy invokedBy2 = new InvokedBy();
		invokedBy2.setUserId(userId_2);
		
		Map<String,Object> activityAttributes2 = new HashMap<>();
		activityAttributes2.put("Key1", 200);
		activityAttributes2.put("Key2", "Test");
		activityAttributes2.put("Key3", 77.55);
		
		// set ActivityBoundary 2
		activityBoundary2.setActivityId(activityGeneralId_2);
		activityBoundary2.setType("adminTestsType");
		activityBoundary2.setInstance(instance_2);
		activityBoundary2.setCreatedTimestamp(java.time.LocalDateTime.now().toString());
		activityBoundary2.setInvokedBy(invokedBy2);
		activityBoundary2.setActivityAttributes(activityAttributes2);

		
		// initialize ActivityBoundary 3
		GeneralId activityGeneralId_3 = new GeneralId();
		activityGeneralId_3.setDomain("2022b.diana.ukrainsky");
		activityGeneralId_3.setId("3");
		
		GeneralId instanceGeneralId_3 = new GeneralId();
		instanceGeneralId_3.setDomain("2022b.diana.ukrainsky");
		instanceGeneralId_3.setId("34");
		
		Instance instance_3 = new Instance();
		instance_3.setInstanceId(instanceGeneralId_3);
		
		UserId userId_3 = new UserId();
		userId_3.setDomain("2022b.diana.ukrainsky");
		userId_3.setEmail("user3@gmail.com");
		
		InvokedBy invokedBy3 = new InvokedBy();
		invokedBy3.setUserId(userId_3);
		
		Map<String,Object> activityAttributes3 = new HashMap<>();
		activityAttributes3.put("Key1", 300);
		activityAttributes3.put("Key2", "Test");
		activityAttributes3.put("Key3", 99.55);
		
		// set ActivityBoundary 3
		activityBoundary3.setActivityId(activityGeneralId_3);
		activityBoundary3.setType("adminTestsType");
		activityBoundary3.setInstance(instance_3);
		activityBoundary3.setCreatedTimestamp(java.time.LocalDateTime.now().toString());
		activityBoundary3.setInvokedBy(invokedBy3);
		activityBoundary3.setActivityAttributes(activityAttributes3);
		
		// ActivityBoundary array
		ActivityBoundary [] activityBoundaryArray = new ActivityBoundary[] {
				activityBoundary1, 
				activityBoundary2, 
				activityBoundary3};

		// HTTP POST ActivityBoundary 1
		ActivityBoundary activityBoundaryReceivedFromPOST1 = this.restTemplate
				.postForObject(
						this.activitiesUrl, 
						activityBoundary1, 
						ActivityBoundary.class);
		
		// HTTP POST ActivityBoundary 2
		ActivityBoundary activityBoundaryReceivedFromPOST2 = this.restTemplate
				.postForObject(
						this.activitiesUrl, 
						activityBoundary2, 
						ActivityBoundary.class);
		
		// HTTP POST ActivityBoundary 3
		ActivityBoundary activityBoundaryReceivedFromPOST3 = this.restTemplate
				.postForObject(
						this.activitiesUrl, 
						activityBoundary3, 
						ActivityBoundary.class);
		
		// Array of ActivityBoundary's received from POST
		ActivityBoundary[] receivedFromPOSTActivityBoundaryArray = new ActivityBoundary[] {
				activityBoundaryReceivedFromPOST1, 
				activityBoundaryReceivedFromPOST2, 
				activityBoundaryReceivedFromPOST3};
		
		// WHEN I GET all Activities
		// HTTP GET All Activities
		ActivityBoundary[] retreivedActivityBoundaryArray = this.restTemplate
				.getForObject(
						this.activitiesUrl,
						ActivityBoundary[].class);

		// THEN the server returns status 2xx
		// AND the initialized activities retrieved
		assertThat(retreivedActivityBoundaryArray).isNotNull();
		
		// check Activities array retrieved contains the activity boundaries initialized
		for(int i = 0; i < activityBoundaryArray.length; i++) {
			
			ActivityBoundary createdActivityBoundery = activityBoundaryArray[i];
			ActivityBoundary retreivedActivityBoundery = receivedFromPOSTActivityBoundaryArray[i];
			
			assertThat(retreivedActivityBoundery.getActivityId().getDomain())
			.isEqualTo(createdActivityBoundery.getActivityId().getDomain());
			
			assertThat(retreivedActivityBoundery.getActivityId().getId())
			.isEqualTo(createdActivityBoundery.getActivityId().getId());
			
			assertThat(retreivedActivityBoundery.getType())
			.isEqualTo(createdActivityBoundery.getType());
			
			assertThat(retreivedActivityBoundery.getInstance().getInstanceId().getDomain())
			.isEqualTo(createdActivityBoundery.getInstance().getInstanceId().getDomain());
			
			assertThat(retreivedActivityBoundery.getInstance().getInstanceId().getId())
			.isEqualTo(createdActivityBoundery.getInstance().getInstanceId().getId());
			
			assertThat(retreivedActivityBoundery.getCreatedTimestamp())
			.isEqualTo(createdActivityBoundery.getCreatedTimestamp());
			
			
			assertThat(retreivedActivityBoundery.getInvokedBy().getUserId())
			.isEqualTo(createdActivityBoundery.getInvokedBy().getUserId());
			
			assertThat(retreivedActivityBoundery.getActivityAttributes().get("Key1"))
			.isNotEqualTo(createdActivityBoundery.getActivityAttributes().get("Key1"));
			
			assertThat(retreivedActivityBoundery.getActivityAttributes().get("Key2"))
			.isNotEqualTo(createdActivityBoundery.getActivityAttributes().get("Key2"));
			
			assertThat(retreivedActivityBoundery.getActivityAttributes().get("Key3"))
			.isNotEqualTo(createdActivityBoundery.getActivityAttributes().get("Key3"));
		}
		
	}
	
	
	@Test
	public void testExportAllUsersActuallyExportsAllUsers() throws Exception {
		// GIVEN the server is up
		// AND the server contains 3 Users
		// initialize 3 newUserBoundary
		
		// initialize newUserBoundary 1
		UserId userId1 = new UserId();
		userId1.setDomain("2022b.diana.ukrainsky");
		userId1.setEmail("user1@gmail.com");
		
		UserBoundary userBoundary1 = new UserBoundary();
		userBoundary1.setAvatar("star");
		userBoundary1.setRole("testMember1");
		userBoundary1.setUsername("First");
		userBoundary1.setUserId(userId1);
		
		// initialize newUserBoundary 2
		UserId userId2 = new UserId();
		userId2.setDomain("2022b.diana.ukrainsky");
		userId2.setEmail("user2@gmail.com");
		
		UserBoundary userBoundary2 = new UserBoundary();
		userBoundary2.setAvatar("moon");
		userBoundary2.setRole("testMember2");
		userBoundary2.setUsername("Second");
		userBoundary2.setUserId(userId2);
		
		// initialize newUserBoundary 3
		UserId userId3 = new UserId();
		userId3.setDomain("2022b.diana.ukrainsky");
		userId3.setEmail("user1@gmail.com");
		
		UserBoundary userBoundary3 = new UserBoundary();
		userBoundary3.setAvatar("tree");
		userBoundary3.setRole("testMember3");
		userBoundary3.setUsername("Thidr");
		userBoundary3.setUserId(userId3);
		
		// Array of usersBoundery's received from POST 
		UserBoundary[] userBoundariesArray = new UserBoundary[] {
				userBoundary1, 
				userBoundary2, 
				userBoundary3};

		
		// HTTP POST newUserBoundary 1
		UserBoundary userBoundaryFromPOST1 = this.restTemplate
				.postForObject(
						this.usersUrl, 
						userBoundary1, 
						UserBoundary.class);
		
		// HTTP POST newUserBoundary 2
		UserBoundary userBoundaryFromPOST2 = this.restTemplate
				.postForObject(
						this.usersUrl, 
						userBoundary2, 
						UserBoundary.class);
		
		// HTTP POST newUserBoundary 3
		UserBoundary userBoundaryFromPOST3 = this.restTemplate
				.postForObject(
						this.usersUrl, 
						userBoundary3, 
						UserBoundary.class);
		
		// WHEN I GET all users
		// Array of usersBoundery's received from POST 
		UserBoundary[] userBoundariesFromPOSTArray = new UserBoundary[] {
				userBoundaryFromPOST1, 
				userBoundaryFromPOST2, 
				userBoundaryFromPOST3};
		
		// HTTP GET All users
		UserBoundary[] retrivedUserBoundaryArray = this.restTemplate
				.getForObject(
						this.usersUrl,
						UserBoundary[].class);

		// THEN the server returns status 2xx
		// AND the initialized users retrieved
		assertThat(retrivedUserBoundaryArray).isNotNull();
		
		// check users array retrieved contains the users boundaries initialized
		for(int i = 0; i < userBoundariesArray.length; i++) {
			UserBoundary createdUserBoundery = userBoundariesArray[i];
			UserBoundary retreivedUserBoundery = userBoundariesFromPOSTArray[i];
			
			assertThat(retreivedUserBoundery.getUserId().getDomain())
			.isEqualTo(createdUserBoundery.getUserId().getDomain());
			
			assertThat(retreivedUserBoundery.getUserId().getEmail())
			.isEqualTo(createdUserBoundery.getUserId().getEmail());
			
			assertThat(retreivedUserBoundery.getAvatar())
			.isEqualTo(createdUserBoundery.getAvatar());
			
			assertThat(retreivedUserBoundery.getRole())
			.isEqualTo(createdUserBoundery.getRole());
			
			assertThat(retreivedUserBoundery.getUsername())
			.isEqualTo(createdUserBoundery.getUsername());
		}

	}
	
}
