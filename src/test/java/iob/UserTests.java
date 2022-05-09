package iob;

import static org.assertj.core.api.Assertions.assertThat;


import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import iob.bounderies.NewUserBoundary;
import iob.bounderies.UserBoundary;
import iob.bounderies.UserId;
import iob.logic.IdConverter;
import iob.logic.UserConverter;
import iob.service.dao.UserDao;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserTests {

	private int port;
	private String url;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IdConverter idConverter;
	
	private RestTemplate restTemplate;
	private String domain;

	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}

	@Value("${spring.application.name:null}")
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	@PostConstruct
	public void testsInit() {
		this.url = "http://localhost:" + this.port + "/iob/users";
		this.restTemplate = new RestTemplate();
	}
	


	@Test
	public void testCreateUserActuallyCreatesAUser() throws Exception {
		// GIVEN the server is up
		// initialize newUserBoundary
		NewUserBoundary newUserBoundary = new NewUserBoundary();
		newUserBoundary.setAvatar("J");
		newUserBoundary.setEmail("newUserBoundary@gmail.com");
		newUserBoundary.setRole("Manager");
		newUserBoundary.setUsername("newUserBoundary");

		
		// WHEN I POST newUserBoundary
		// POST the newUserBoundary	
		UserBoundary postReturnedUserBoundary = this.restTemplate
				.postForObject(this.url, newUserBoundary, UserBoundary.class);
		// THEN the server returns status 2xx
		// GET the UserBoundary
		UserBoundary retrivedUserBoundary = this.restTemplate
				.getForObject(this.url+"/login/{userDomain}/{userEmail}",
						UserBoundary.class,
						postReturnedUserBoundary.getUserId().getDomain(),
						postReturnedUserBoundary.getUserId().getEmail());
		
		
		assertThat(retrivedUserBoundary).isNotNull();
		// AND the server created userId for the posted UserBoundary 
		// check that userId created
		assertThat(retrivedUserBoundary.getUserId()).isNotNull();
		// AND the attributes of the UserBoundary equal to the posted newUserBoundary
		// check that attributes are the same
		assertThat(retrivedUserBoundary.getUserId().getDomain())
		.isEqualTo(postReturnedUserBoundary.getUserId().getDomain());
		
		assertThat(retrivedUserBoundary.getUserId().getEmail())
		.isEqualTo(postReturnedUserBoundary.getUserId().getEmail());
		
		assertThat(retrivedUserBoundary.getAvatar())
		.isEqualTo(postReturnedUserBoundary.getAvatar());
		
		assertThat(retrivedUserBoundary.getRole())
		.isEqualToIgnoringCase(postReturnedUserBoundary.getRole());
		
		//Delete 
		userDao.deleteById(
				idConverter.getUserEntityIdFromDomainAndEmail(
						postReturnedUserBoundary.getUserId().getDomain(),
						postReturnedUserBoundary.getUserId().getEmail()));
	}
	
	@Test
	public void testRetriveUserActuallyRetrivesTheUser() throws Exception {
		// GIVEN the server is up
		// AND the server contains 2 Users
		//initialize 2 newUserBoundary
		
		//initialize newUserBoundary 1
		NewUserBoundary newUserBoundary1 = new NewUserBoundary();
		newUserBoundary1.setAvatar("J");
		newUserBoundary1.setEmail("newUserBoundary1@gmail.com");
		newUserBoundary1.setRole("Manager");
		newUserBoundary1.setUsername("newUserBoundary1");

		
		
		//initialize newUserBoundary 2
		NewUserBoundary newUserBoundary2 = new NewUserBoundary();
		newUserBoundary2.setAvatar("J");
		newUserBoundary2.setEmail("newUserBoundary2@gmail.com");
		newUserBoundary2.setRole("Manager");
		newUserBoundary2.setUsername("newUserBoundary2");
		
		//HTTP POST newUserBoundary 1
		UserBoundary postReturnedUserBoundary1 = this.restTemplate
				.postForObject(this.url, newUserBoundary1, UserBoundary.class);
		
		//HTTP POST newUserBoundary 2
		UserBoundary postReturnedUserBoundary2 = this.restTemplate
				.postForObject(this.url, newUserBoundary2, UserBoundary.class);

		
		// WHEN I GET UserBoundary1
		// AND GET UserBoundary2

		

		//HTTP GET UserBoundary 1
		UserBoundary retrivedUserBoundary1 = this.restTemplate
				.getForObject(this.url+"/login/{userDomain}/{userEmail}",
						UserBoundary.class,
						postReturnedUserBoundary1.getUserId().getDomain(),
						postReturnedUserBoundary1.getUserId().getEmail());
		
		
		//HTTP GET UserBoundary 2
		UserBoundary retrivedUserBoundary2 = this.restTemplate
				.getForObject(this.url+"/login/{userDomain}/{userEmail}",
						UserBoundary.class,
						postReturnedUserBoundary2.getUserId().getDomain(),
						postReturnedUserBoundary2.getUserId().getEmail());
		

		
		assertThat(retrivedUserBoundary1).isNotNull();
		// AND the server created userId for the posted UserBoundary 
		// check that userId created
		assertThat(retrivedUserBoundary1.getUserId()).isNotNull();
		// AND the attributes of the UserBoundary equal to the posted newUserBoundary
		// check that attributes are the same
		assertThat(retrivedUserBoundary1.getUserId().getDomain())
		.isEqualTo(postReturnedUserBoundary1.getUserId().getDomain());
		
		assertThat(retrivedUserBoundary1.getUserId().getEmail())
		.isEqualTo(postReturnedUserBoundary1.getUserId().getEmail());
		
		assertThat(retrivedUserBoundary1.getAvatar())
		.isEqualTo(postReturnedUserBoundary1.getAvatar());
		
		assertThat(retrivedUserBoundary1.getRole())
		.isEqualToIgnoringCase(postReturnedUserBoundary1.getRole());
		
		assertThat(retrivedUserBoundary2).isNotNull();
		// AND the server created userId for the posted UserBoundary 
		// check that userId created
		assertThat(retrivedUserBoundary2.getUserId()).isNotNull();
		// AND the attributes of the UserBoundary equal to the posted newUserBoundary
		// check that attributes are the same
		assertThat(retrivedUserBoundary2.getUserId().getDomain())
		.isEqualTo(postReturnedUserBoundary2.getUserId().getDomain());
		
		assertThat(retrivedUserBoundary2.getUserId().getEmail())
		.isEqualTo(postReturnedUserBoundary2.getUserId().getEmail());
		
		assertThat(retrivedUserBoundary2.getAvatar())
		.isEqualTo(postReturnedUserBoundary2.getAvatar());
		
		assertThat(retrivedUserBoundary2.getRole())
		.isEqualToIgnoringCase(postReturnedUserBoundary2.getRole());

		//Delete 
		userDao.deleteById(
				idConverter.getUserEntityIdFromDomainAndEmail(
						postReturnedUserBoundary1.getUserId().getDomain(),
						postReturnedUserBoundary1.getUserId().getEmail()));
		
		//Delete 
		userDao.deleteById(
				idConverter.getUserEntityIdFromDomainAndEmail(
						postReturnedUserBoundary2.getUserId().getDomain(),
						postReturnedUserBoundary2.getUserId().getEmail()));
	}
	
	
	@Test
	public void testUpdateUserActuallyUpdatesAUser() throws Exception {
		// GIVEN the server is up
	    // AND the server contains a User with a known id	
		// initialize newUserBoundary
		NewUserBoundary newUserBoundary = new NewUserBoundary();
		newUserBoundary.setAvatar("J");
		newUserBoundary.setEmail("newUserBoundary@gmail.com");
		newUserBoundary.setRole("Manager");
		newUserBoundary.setUsername("newUserBoundary");
		
		
		// HTTP POST newUserBoundary
		UserBoundary postReturnedUserBoundary = this.restTemplate
				.postForObject(this.url, newUserBoundary, UserBoundary.class);
		
		
		// initialize update UserBoundary object
		
		UserId userId = new UserId();
		userId.setDomain(this.domain);
		userId.setEmail("updatedUserBoundary@gmail.com");
		
		UserBoundary updatedUserBoundary = new UserBoundary();
		updatedUserBoundary.setAvatar("Test");
		updatedUserBoundary.setUserId(userId);
		updatedUserBoundary.setRole("Player");
		updatedUserBoundary.setUsername("Test New user name");
		
		
		// WHEN I PUT UserBoundary update
		// HTTP PUT update UserBoundary
		this.restTemplate
		.put(this.url + "/{userDomain}/{userEmail}",
				updatedUserBoundary,
				postReturnedUserBoundary.getUserId().getDomain(), 
				postReturnedUserBoundary.getUserId().getEmail());


		// THEN the server returns status 2xx
		// HTTP GET UserBoundary
		UserBoundary retrivedUserBoundary = this.restTemplate
				.getForObject(this.url+"/login/{userDomain}/{userEmail}",
						UserBoundary.class,
						postReturnedUserBoundary.getUserId().getDomain(),
						postReturnedUserBoundary.getUserId().getEmail());
		
		assertThat(retrivedUserBoundary).isNotNull();
		assertThat(retrivedUserBoundary.getUserId()).isNotNull();
		
		// AND the relevant attributes updated 
		// check that relevant attributes updated
		assertThat(retrivedUserBoundary.getUsername())
		.isEqualTo(updatedUserBoundary.getUsername());
		
		assertThat(retrivedUserBoundary.getAvatar())
		.isEqualTo(updatedUserBoundary.getAvatar());
		
		assertThat(retrivedUserBoundary.getRole())
		.isEqualToIgnoringCase(updatedUserBoundary.getRole());
	

		// AND the attributes that not updated stayed the same
		// check that unchangeable attributes not updated

		
		assertThat(retrivedUserBoundary.getUserId().getEmail())
		.isNotEqualTo(updatedUserBoundary.getUserId().getEmail());
		
		//Delete 
		userDao.deleteById(
				idConverter.getUserEntityIdFromDomainAndEmail(
						postReturnedUserBoundary.getUserId().getDomain(),
						postReturnedUserBoundary.getUserId().getEmail()));

	}
	
}
