//package iob;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import java.util.HashMap;
//import java.util.Map;
//import javax.annotation.PostConstruct;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.web.client.RestTemplate;
//import iob.bounderies.ActivityBoundary;
//import iob.bounderies.CreatedBy;
//import iob.bounderies.GeneralId;
//import iob.bounderies.Instance;
//import iob.bounderies.InstanceBoundary;
//import iob.bounderies.InvokedBy;
//import iob.bounderies.UserBoundary;
//import iob.bounderies.UserId;
//import iob.bounderies.NewUserBoundary;
//
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//public class AdminTests {
//	private int port;
//	private String activitiesAdminUrl;
//	private String usersAdminUrl;
//	private String postUserUrl;
//	private String instancesUrl;
//	private String postActivityUrl;
//	private String instancesAdminUrl;
//	private RestTemplate restTemplate;
//	private String domain;
//
//	@LocalServerPort
//	public void setPort(int port) {
//		this.port = port;
//	}
//	
//	@Value("${spring.application.name:null}")
//	public void setDomain(String domain) {
//		this.domain = domain;
//	}
//
//	@PostConstruct
//	public void testsInit() {
//		this.activitiesAdminUrl = "http://localhost:" + this.port + "/iob/admin/activities";
//		this.usersAdminUrl = "http://localhost:" + this.port + "/iob/admin/users";
//		this.postUserUrl = "http://localhost:" + this.port + "/iob/users";
//		this.instancesUrl = "http://localhost:" + this.port + "/iob/instances";
//		this.postActivityUrl = "http://localhost:" + this.port + "/iob/activities";
//		this.instancesAdminUrl = "http://localhost:" + this.port + "/iob/admin/instances";
//		this.restTemplate = new RestTemplate();
//	}
//
//
//
//	@Test
//	public void testDeleteAllUsersActuallyDeletesAllUsers() throws Exception {
//		// GIVEN the server is up
//		// AND the server contains 3 Users
//
//		NewUserBoundary newUserBoundary1 = new NewUserBoundary();
//		newUserBoundary1.setAvatar("avatar1");
//		newUserBoundary1.setEmail("newUserBoundary1@gmail.com");
//		newUserBoundary1.setRole("ADMIN");
//		newUserBoundary1.setUsername("newUserBoundary1");
//
//		NewUserBoundary newUserBoundary2 = new NewUserBoundary();
//		newUserBoundary2.setAvatar("avatar2");
//		newUserBoundary2.setEmail("newUserBoundary2@gmail.com");
//		newUserBoundary2.setRole("ADMIN");
//		newUserBoundary2.setUsername("newUserBoundary2");
//
//		NewUserBoundary newUserBoundary3 = new NewUserBoundary();
//		newUserBoundary3.setAvatar("avatar3");
//		newUserBoundary3.setEmail("newUserBoundary3@gmail.com");
//		newUserBoundary3.setRole("ADMIN");
//		newUserBoundary3.setUsername("newUserBoundary3");
//
//		UserBoundary postReturnedAdminUserBoundary = this.restTemplate.postForObject(this.postUserUrl, newUserBoundary1, UserBoundary.class);
//
//		this.restTemplate.postForObject(this.postUserUrl, newUserBoundary2, UserBoundary.class);
//
//		this.restTemplate.postForObject(this.postUserUrl, newUserBoundary3, UserBoundary.class);
//
//		// WHEN I DELETE all users
//		this.restTemplate.delete(
//				this.usersAdminUrl + "?userDomain={domain}&userEmail={email}",
//				postReturnedAdminUserBoundary.getUserId().getDomain(),
//				postReturnedAdminUserBoundary.getUserId().getEmail());
//
//		UserBoundary[] usersOnServer = this.restTemplate.getForObject(this.usersAdminUrl, UserBoundary[].class);
//
//		// THEN the server returns status 2xx
//		// AND there are no users in the domain
//		assertThat(usersOnServer).isEmpty();
//	}
//
//	@Test
//	public void testDeleteAllInstancesActuallyDeletesAllInstances() throws Exception {
//		// GIVEN the server is up
//		// AND the server contains 3 Instances
//		// initialize 3 InstanceBoundary
//
//		// instanceBoundary 1
//		UserId userId1 = new UserId();
//		userId1.setEmail("Test1@gmail.com");
//		userId1.setDomain(this.domain);
//
//		CreatedBy createdBy1 = new CreatedBy();
//		createdBy1.setUserId(userId1);
//
//		InstanceBoundary postInstanceBoundary1 = new InstanceBoundary();
//		postInstanceBoundary1.setInstanceId(null);
//		postInstanceBoundary1.setCreatedBy(createdBy1);
//		postInstanceBoundary1.setType("Test Type");
//		postInstanceBoundary1.setName("Test Name");
//		postInstanceBoundary1.setActive(true);
//
//		// instanceBoundary 2
//		UserId userId2 = new UserId();
//		userId2.setEmail("Test2@gmail.com");
//		userId2.setDomain(this.domain);
//
//		CreatedBy createdBy2 = new CreatedBy();
//		createdBy2.setUserId(userId2);
//
//		InstanceBoundary postInstanceBoundary2 = new InstanceBoundary();
//		postInstanceBoundary2.setInstanceId(null);
//		postInstanceBoundary2.setCreatedBy(createdBy2);
//		postInstanceBoundary2.setType("Test Type");
//		postInstanceBoundary2.setName("Test Name");
//		postInstanceBoundary2.setActive(true);
//
//		// instanceBoundary 3
//		UserId userId3 = new UserId();
//		userId3.setEmail("Test3@gmail.com");
//		userId3.setDomain(this.domain);
//
//		CreatedBy createdBy3 = new CreatedBy();
//		createdBy3.setUserId(userId3);
//
//		InstanceBoundary postInstanceBoundary3 = new InstanceBoundary();
//		postInstanceBoundary3.setInstanceId(null);
//		postInstanceBoundary3.setCreatedBy(createdBy3);
//		postInstanceBoundary3.setType("Test Type");
//		postInstanceBoundary3.setName("Test Name");
//		postInstanceBoundary3.setActive(true);
//
//		// HTTP POST
//		this.restTemplate.postForObject(this.instancesUrl, postInstanceBoundary1, InstanceBoundary.class);
//		// HTTP POST
//		this.restTemplate.postForObject(this.instancesUrl, postInstanceBoundary2, InstanceBoundary.class);
//
//		// HTTP POST
//		this.restTemplate.postForObject(this.instancesUrl, postInstanceBoundary3, InstanceBoundary.class);
//
//		// WHEN I DELETE all Instances
//		this.restTemplate.delete(this.instancesAdminUrl);
//
//		// HTTP GET All Instances
//		InstanceBoundary instanceBoundaryArray[] = this.restTemplate.getForObject(this.instancesUrl,
//				InstanceBoundary[].class);
//
//		// THEN the server returns status 2xx
//		// AND there are no Instances in the domain
//		// check Instances array retrieved is empty
//		assertThat(instanceBoundaryArray).isNotNull();
//		assertThat(instanceBoundaryArray).isEmpty();
//
//	}
//
//	@Test
//	public void testDeleteAllActivitiesActuallyDeletesAllActivities() throws Exception {
//		// GIVEN the server is up
//		// AND the server contains 3 Activities
//
//		ActivityBoundary activityBoundary1 = new ActivityBoundary();
//		ActivityBoundary activityBoundary2 = new ActivityBoundary();
//		ActivityBoundary activityBoundary3 = new ActivityBoundary();
//
//		// initialize ActivityBoundary 1
//
//		GeneralId instanceGeneralId_1 = new GeneralId();
//		instanceGeneralId_1.setDomain(this.domain);
//		instanceGeneralId_1.setId("12");
//
//		Instance instance_1 = new Instance();
//		instance_1.setInstanceId(instanceGeneralId_1);
//
//		UserId userId_1 = new UserId();
//		userId_1.setDomain(this.domain);
//		userId_1.setEmail("user1@gmail.com");
//
//		InvokedBy invokedBy1 = new InvokedBy();
//		invokedBy1.setUserId(userId_1);
//
//		Map<String, Object> activityAttributes1 = new HashMap<>();
//		activityAttributes1.put("Key1", 100);
//		activityAttributes1.put("Key2", "Test");
//		activityAttributes1.put("Key3", 55.55);
//
//		// set ActivityBoundary 1
//		activityBoundary1.setType("adminTestsType");
//		activityBoundary1.setInstance(instance_1);
//		activityBoundary1.setInvokedBy(invokedBy1);
//		activityBoundary1.setActivityAttributes(activityAttributes1);
//
//		// initialize ActivityBoundary 2
//
//		GeneralId instanceGeneralId_2 = new GeneralId();
//		instanceGeneralId_2.setDomain(this.domain);
//		instanceGeneralId_2.setId("23");
//
//		Instance instance_2 = new Instance();
//		instance_2.setInstanceId(instanceGeneralId_2);
//
//		UserId userId_2 = new UserId();
//		userId_2.setDomain(this.domain);
//		userId_2.setEmail("user2@gmail.com");
//
//		InvokedBy invokedBy2 = new InvokedBy();
//		invokedBy2.setUserId(userId_2);
//
//		Map<String, Object> activityAttributes2 = new HashMap<>();
//		activityAttributes2.put("Key1", 200);
//		activityAttributes2.put("Key2", "Test");
//		activityAttributes2.put("Key3", 77.55);
//
//		// set ActivityBoundary 2
//		activityBoundary2.setType("adminTestsType");
//		activityBoundary2.setInstance(instance_2);
//		activityBoundary2.setInvokedBy(invokedBy2);
//		activityBoundary2.setActivityAttributes(activityAttributes2);
//
//		// initialize ActivityBoundary 3
//
//		GeneralId instanceGeneralId_3 = new GeneralId();
//		instanceGeneralId_3.setDomain(this.domain);
//		instanceGeneralId_3.setId("34");
//
//		Instance instance_3 = new Instance();
//		instance_3.setInstanceId(instanceGeneralId_3);
//
//		UserId userId_3 = new UserId();
//		userId_3.setDomain(this.domain);
//		userId_3.setEmail("user3@gmail.com");
//
//		InvokedBy invokedBy3 = new InvokedBy();
//		invokedBy3.setUserId(userId_3);
//
//		Map<String, Object> activityAttributes3 = new HashMap<>();
//		activityAttributes3.put("Key1", 300);
//		activityAttributes3.put("Key2", "Test");
//		activityAttributes3.put("Key3", 99.55);
//
//		// set ActivityBoundary 3
//		activityBoundary3.setType("adminTestsType");
//		activityBoundary3.setInstance(instance_3);
//		activityBoundary3.setInvokedBy(invokedBy3);
//		activityBoundary3.setActivityAttributes(activityAttributes3);
//
//		// HTTP POST ActivityBoundary 1
//		this.restTemplate.postForObject(this.postActivityUrl, activityBoundary1, ActivityBoundary.class);
//
//		// HTTP POST ActivityBoundary 2
//		this.restTemplate.postForObject(this.postActivityUrl, activityBoundary2, ActivityBoundary.class);
//
//		// HTTP POST ActivityBoundary 3
//		this.restTemplate.postForObject(this.postActivityUrl, activityBoundary3, ActivityBoundary.class);
//
//		// WHEN I DELETE all Activities
//		this.restTemplate.delete(this.activitiesAdminUrl);
//
//		// HTTP GET All Activities
//		ActivityBoundary activityBoundaryArray[] = this.restTemplate.getForObject(this.activitiesAdminUrl,
//				ActivityBoundary[].class);
//
//		// THEN the server returns status 2xx
//		// AND there are no Activities in the domain
//		// check Activities array retrieved is empty
//		assertThat(activityBoundaryArray).isNotNull();
//		assertThat(activityBoundaryArray).isEmpty();
//	}
//
//	@Test
//	public void testExportAllActivitiesActuallyExportsAllActivities() throws Exception {
//		// GIVEN the server is up
//		// AND the server contains 3 Activities
//		// initialize 3 ActivityBoundary
//
//		ActivityBoundary activityBoundary1 = new ActivityBoundary();
//		ActivityBoundary activityBoundary2 = new ActivityBoundary();
//		ActivityBoundary activityBoundary3 = new ActivityBoundary();
//
//		// initialize ActivityBoundary 1
//
//		GeneralId instanceGeneralId_1 = new GeneralId();
//		instanceGeneralId_1.setDomain(this.domain);
//		instanceGeneralId_1.setId("12");
//
//		Instance instance_1 = new Instance();
//		instance_1.setInstanceId(instanceGeneralId_1);
//
//		UserId userId_1 = new UserId();
//		userId_1.setDomain(this.domain);
//		userId_1.setEmail("user1@gmail.com");
//
//		InvokedBy invokedBy1 = new InvokedBy();
//		invokedBy1.setUserId(userId_1);
//
//		Map<String, Object> activityAttributes1 = new HashMap<>();
//		activityAttributes1.put("Key1", 100);
//		activityAttributes1.put("Key2", "Test");
//		activityAttributes1.put("Key3", 55.55);
//
//		// set ActivityBoundary 1
//		activityBoundary1.setType("adminTestsType");
//		activityBoundary1.setInstance(instance_1);
//		activityBoundary1.setInvokedBy(invokedBy1);
//		activityBoundary1.setActivityAttributes(activityAttributes1);
//
//		// initialize ActivityBoundary 2
//
//		GeneralId instanceGeneralId_2 = new GeneralId();
//		instanceGeneralId_2.setDomain(this.domain);
//		instanceGeneralId_2.setId("23");
//
//		Instance instance_2 = new Instance();
//		instance_2.setInstanceId(instanceGeneralId_2);
//
//		UserId userId_2 = new UserId();
//		userId_2.setDomain(this.domain);
//		userId_2.setEmail("user2@gmail.com");
//
//		InvokedBy invokedBy2 = new InvokedBy();
//		invokedBy2.setUserId(userId_2);
//
//		Map<String, Object> activityAttributes2 = new HashMap<>();
//		activityAttributes2.put("Key1", 200);
//		activityAttributes2.put("Key2", "Test");
//		activityAttributes2.put("Key3", 77.55);
//
//		// set ActivityBoundary 2
//		activityBoundary2.setType("adminTestsType");
//		activityBoundary2.setInstance(instance_2);
//		activityBoundary2.setInvokedBy(invokedBy2);
//		activityBoundary2.setActivityAttributes(activityAttributes2);
//
//		// initialize ActivityBoundary 3
//
//		GeneralId instanceGeneralId_3 = new GeneralId();
//		instanceGeneralId_3.setDomain(this.domain);
//		instanceGeneralId_3.setId("34");
//
//		Instance instance_3 = new Instance();
//		instance_3.setInstanceId(instanceGeneralId_3);
//
//		UserId userId_3 = new UserId();
//		userId_3.setDomain(this.domain);
//		userId_3.setEmail("user3@gmail.com");
//
//		InvokedBy invokedBy3 = new InvokedBy();
//		invokedBy3.setUserId(userId_3);
//
//		Map<String, Object> activityAttributes3 = new HashMap<>();
//		activityAttributes3.put("Key1", 300);
//		activityAttributes3.put("Key2", "Test");
//		activityAttributes3.put("Key3", 99.55);
//
//		// set ActivityBoundary 3
//		activityBoundary3.setType("adminTestsType");
//		activityBoundary3.setInstance(instance_3);
//		activityBoundary3.setInvokedBy(invokedBy3);
//		activityBoundary3.setActivityAttributes(activityAttributes3);
//
//		// ActivityBoundary array
//		ActivityBoundary[] activityBoundaryArray = new ActivityBoundary[] { activityBoundary1, activityBoundary2,
//				activityBoundary3 };
//
//		// HTTP POST ActivityBoundary 1
//		ActivityBoundary activityBoundaryReceivedFromPOST1 = this.restTemplate.postForObject(this.postActivityUrl,
//				activityBoundary1, ActivityBoundary.class);
//
//		// HTTP POST ActivityBoundary 2
//		ActivityBoundary activityBoundaryReceivedFromPOST2 = this.restTemplate.postForObject(this.postActivityUrl,
//				activityBoundary2, ActivityBoundary.class);
//
//		// HTTP POST ActivityBoundary 3
//		ActivityBoundary activityBoundaryReceivedFromPOST3 = this.restTemplate.postForObject(this.postActivityUrl,
//				activityBoundary3, ActivityBoundary.class);
//
//		// Array of ActivityBoundary's received from POST
//		ActivityBoundary[] receivedFromPOSTActivityBoundaryArray = new ActivityBoundary[] {
//				activityBoundaryReceivedFromPOST1, activityBoundaryReceivedFromPOST2,
//				activityBoundaryReceivedFromPOST3 };
//
//		// WHEN I GET all Activities
//		// HTTP GET All Activities
//		ActivityBoundary[] retreivedActivityBoundaryArray = this.restTemplate.getForObject(this.activitiesAdminUrl,
//				ActivityBoundary[].class);
//
//		// THEN the server returns status 2xx
//		// AND the initialized activities retrieved
//		assertThat(retreivedActivityBoundaryArray).isNotNull();
//
//		// check Activities array retrieved contains the activity boundaries initialized
//		for (int i = 0; i < activityBoundaryArray.length; i++) {
//
//			ActivityBoundary createdActivityBoundery = activityBoundaryArray[i];
//			ActivityBoundary retreivedActivityBoundery = receivedFromPOSTActivityBoundaryArray[i];
//
//			assertThat(retreivedActivityBoundery.getType()).isEqualTo(createdActivityBoundery.getType());
//
//			assertThat(retreivedActivityBoundery.getInstance().getInstanceId().getDomain())
//					.isEqualTo(createdActivityBoundery.getInstance().getInstanceId().getDomain());
//
//			assertThat(retreivedActivityBoundery.getInstance().getInstanceId().getId())
//					.isEqualTo(createdActivityBoundery.getInstance().getInstanceId().getId());
//
//			assertThat(retreivedActivityBoundery.getInvokedBy().getUserId().getDomain())
//					.isEqualTo(createdActivityBoundery.getInvokedBy().getUserId().getDomain());
//
//			assertThat(retreivedActivityBoundery.getInvokedBy().getUserId().getEmail())
//					.isEqualTo(createdActivityBoundery.getInvokedBy().getUserId().getEmail());
//
//			assertThat(retreivedActivityBoundery.getActivityAttributes().get("Key1"))
//					.isEqualTo(createdActivityBoundery.getActivityAttributes().get("Key1"));
//
//			assertThat(retreivedActivityBoundery.getActivityAttributes().get("Key2"))
//					.isEqualTo(createdActivityBoundery.getActivityAttributes().get("Key2"));
//
//			assertThat(retreivedActivityBoundery.getActivityAttributes().get("Key3"))
//					.isEqualTo(createdActivityBoundery.getActivityAttributes().get("Key3"));
//		}
//
//	}
//
//	@Test
//	public void testExportAllUsersActuallyExportsAllUsers() throws Exception {
//		// GIVEN the server is up
//		// AND the server contains 3 Users
//		// initialize 3 newUserBoundary
//
//		// initialize newUserBoundary 1
//		NewUserBoundary userBoundary1 = new NewUserBoundary();
//		userBoundary1.setAvatar("star");
//		userBoundary1.setRole("Player");
//		userBoundary1.setUsername("First");
//		userBoundary1.setEmail("userBoudary1@gmail.com");
//
//		// initialize newUserBoundary 2
//		NewUserBoundary userBoundary2 = new NewUserBoundary();
//		userBoundary2.setAvatar("moon");
//		userBoundary2.setRole("Manager");
//		userBoundary2.setUsername("Second");
//		userBoundary2.setEmail("userBoudary2@gmail.com");
//
//		// initialize newUserBoundary 3
//
//		NewUserBoundary userBoundary3 = new NewUserBoundary();
//		userBoundary3.setAvatar("tree");
//		userBoundary3.setRole("admin");
//		userBoundary3.setUsername("Third");
//		userBoundary3.setEmail("userBoudary3@gmail.com");
//
//		// Array of usersBoundery's received from POST
//		NewUserBoundary[] createdUserBoundaryArray = new NewUserBoundary[] { userBoundary1, userBoundary2,
//				userBoundary3 };
//
//		// HTTP POST newUserBoundary 1
//		UserBoundary userBoundaryFromPOST1 = this.restTemplate.postForObject(this.postUserUrl, userBoundary1,
//				UserBoundary.class);
//
//		// HTTP POST newUserBoundary 2
//		UserBoundary userBoundaryFromPOST2 = this.restTemplate.postForObject(this.postUserUrl, userBoundary2,
//				UserBoundary.class);
//
//		// HTTP POST newUserBoundary 3
//		UserBoundary userBoundaryFromPOST3 = this.restTemplate.postForObject(this.postUserUrl, userBoundary3,
//				UserBoundary.class);
//
//		// WHEN I GET all users
//		// Array of usersBoundery's received from POST
//		UserBoundary[] userBoundariesFromPOSTArray = new UserBoundary[] { userBoundaryFromPOST1, userBoundaryFromPOST2,
//				userBoundaryFromPOST3 };
//
//		// HTTP GET All users
//		UserBoundary[] retrivedUserBoundaryArray = this.restTemplate.getForObject(this.usersAdminUrl,
//				UserBoundary[].class);
//
//		// THEN the server returns status 2xx
//		// AND the initialized users retrieved
//		assertThat(retrivedUserBoundaryArray).isNotNull();
//
//		// check users array retrieved contains the users boundaries initialized
//		for (int i = 0; i < createdUserBoundaryArray.length; i++) {
//			NewUserBoundary createdUserBoundery = createdUserBoundaryArray[i];
//			UserBoundary retreivedUserBoundery = retrivedUserBoundaryArray[i];
//			UserBoundary postReturnedUserBoundery = userBoundariesFromPOSTArray[i];
//
//			assertThat(retreivedUserBoundery.getUserId().getDomain())
//					.isEqualTo(postReturnedUserBoundery.getUserId().getDomain());
//
//			assertThat(retreivedUserBoundery.getUserId().getEmail())
//					.isEqualTo(postReturnedUserBoundery.getUserId().getEmail());
//
//			assertThat(retreivedUserBoundery.getAvatar()).isEqualTo(createdUserBoundery.getAvatar());
//
//			assertThat(retreivedUserBoundery.getRole()).isEqualToIgnoringCase(createdUserBoundery.getRole());
//
//			assertThat(retreivedUserBoundery.getUsername()).isEqualTo(createdUserBoundery.getUsername());
//		}
//
//	}
//
//}
