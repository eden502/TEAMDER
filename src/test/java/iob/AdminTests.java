package iob;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import iob.bounderies.NewUserBoundary;
import iob.bounderies.UserBoundary;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminTests {
	private int port;
	private String activitiesUrl;
	private String usersUrl;
	private String postUsersUrl;
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
		this.postUsersUrl ="http://localhost:" + this.port + "/iob/users";
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
		
		NewUserBoundary newUserBoundary1 = new NewUserBoundary();
		newUserBoundary1.setAvatar("");
		newUserBoundary1.setEmail("newUserBoundary1@gmail.com");
		newUserBoundary1.setRole("");
		newUserBoundary1.setUsername("newUserBoundary1");
		
		NewUserBoundary newUserBoundary2 = new NewUserBoundary();
		newUserBoundary2.setAvatar("");
		newUserBoundary2.setEmail("newUserBoundary2@gmail.com");
		newUserBoundary2.setRole("");
		newUserBoundary2.setUsername("newUserBoundary2");
		
		NewUserBoundary newUserBoundary3 = new NewUserBoundary();
		newUserBoundary2.setAvatar("");
		newUserBoundary2.setEmail("newUserBoundary3@gmail.com");
		newUserBoundary2.setRole("");
		newUserBoundary2.setUsername("newUserBoundary3");
		
		UserBoundary postReturnedUserBoundary1 = this.restTemplate
				.postForObject(this.postUsersUrl, newUserBoundary1, UserBoundary.class);
		
		UserBoundary postReturnedUserBoundary2 = this.restTemplate
				.postForObject(this.postUsersUrl, newUserBoundary2, UserBoundary.class);
		
		UserBoundary postReturnedUserBoundary3 = this.restTemplate
				.postForObject(this.postUsersUrl, newUserBoundary3, UserBoundary.class);
		
		// WHEN I DELETE all users
		this.restTemplate.delete(this.usersUrl);
		
		UserBoundary[] usersOnServer=this.restTemplate	
				.getForObject(this.usersUrl, UserBoundary[].class );
		
		// THEN the server returns status 2xx
		// AND there are no users in the domain
		assertThat(usersOnServer).isEmpty();
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
		// TODO initialize 3 ActivityBoundary
		
		//TODO initialize ActivityBoundary 1

		
		
		//TODO initialize ActivityBoundary 2

		
		//TODO initialize ActivityBoundary 3
		
		
		
		//TODO HTTP POST ActivityBoundary 1

		
		//TODO HTTP POST ActivityBoundary 2

		
		//TODO HTTP POST ActivityBoundary 3

		
		
		// WHEN I GET all Activities
		

		
		//TODO HTTP GET All Activities

		
		
		// THEN the server returns status 2xx
		// AND the initialized activities retrieved
		// TODO check Activities array retrieved contains the activity boundaries initialized

	}
	
	
	@Test
	public void testExportAllUsersActuallyExportsAllUsers() throws Exception {
		// GIVEN the server is up
		// AND the server contains 3 Users
		// TODO initialize 3 newUserBoundary
		
		//TODO initialize newUserBoundary 1

		
		
		//TODO initialize newUserBoundary 2

		
		//TODO initialize newUserBoundary 3
		
		
		
		//TODO HTTP POST newUserBoundary 1

		
		//TODO HTTP POST newUserBoundary 2

		
		//TODO HTTP POST newUserBoundary 3

		
		
		// WHEN I GET all users
		

		
		//TODO HTTP GET All users

		
		
		// THEN the server returns status 2xx
		// AND the initialized users retrieved
		// TODO check users array retrieved contains the users boundaries initialized


	}
	
}
