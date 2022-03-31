package iob;

import static org.assertj.core.api.Assertions.assertThat;



import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserTests {

	private int port;
	private String url;
	private String deleteUrl;
	private RestTemplate restTemplate;
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}
	
	@PostConstruct
	public void testsInit() {
		this.url = "http://localhost:" + this.port + "/iob/users";
		this.deleteUrl = "http://localhost:" + this.port + "/iob/admin/users";
		this.restTemplate = new RestTemplate();
	}
	

	@AfterEach
	public void tearDown() {
		this.restTemplate
			.delete(this.deleteUrl);
	}
	
	@Test
	public void testCreateUserActuallyCreatesAUser() throws Exception {
		// GIVEN the server is up
		// TODO initialize newUserBoundary 

		
		// WHEN I POST newUserBoundary
		// TODO POST the newUserBoundary	

		// THEN the server returns status 2xx
		// TODO GET the UserBoundary

		
		// AND the server created userId for the posted UserBoundary 
		// TODO check that userId created

		// AND the attributes of the UserBoundary equal to the posted newUserBoundary
		// TODO check that attributes are the same


	}
	
	@Test
	public void testRetriveUserActuallyRetrivesTheUser() throws Exception {
		// GIVEN the server is up
		// AND the server contains 2 Users
		// TODO initialize 2 newUserBoundary
		
		//TODO initialize newUserBoundary 1

		
		
		//TODO initialize newUserBoundary 2

		
		//TODO HTTP POST newUserBoundary 1

		
		//TODO HTTP POST newUserBoundary 2


		
		
		// WHEN I GET UserBoundary1
		// AND GET UserBoundary2
		
		//TODO HTTP GET UserBoundary 1

		
		//TODO HTTP GET UserBoundary 2


		
		
		// THEN the server returns status 2xx
		// AND the server retrieved the 2 UserBoundaries
		// TODO check valid users retrieved

		
		// AND the attributes of the User Boundaries are the correct ones
		// TODO check that attributes are the same as posted 
		

	
	}
	
	
	@Test
	public void testUpdateUserActuallyUpdatesAUser() throws Exception {
		// GIVEN the server is up
	    // AND the server contains a User with a known id	
		// TODO initialize newUserBoundary
		
		
		
		//TODO HTTP POST newUserBoundary

		
		
		//TODO initialize update UserBoundary object

		
		
		// WHEN I PUT UserBoundary update
		// TODO HTTP PUT update UserBoundary


		// THEN the server returns status 2xx
		// TODO HTTP GET UserBoundary
		
		
		// AND the relevant attributes updated 
		// TODO check that relevant attributes updated

		// AND the attributes that not updated stayed the same 
		// TODO check that unchangeable attributes not updated
		

		


	}
	
}
