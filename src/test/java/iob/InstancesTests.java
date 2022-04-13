package iob;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import iob.bounderies.CreatedBy;
import iob.bounderies.GeneralId;
import iob.bounderies.InstanceBoundary;
import iob.bounderies.Location;
import iob.bounderies.UserId;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class InstancesTests {
	
	private int port;
	private String url;
	private String deleteUrl;
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
		this.url = "http://localhost:" + this.port + "/iob/instances";
		this.deleteUrl = "http://localhost:" + this.port + "/iob/admin/instances";
		this.restTemplate = new RestTemplate();
	}
	

	@AfterEach
	public void tearDown() {
		this.restTemplate
			.delete(this.deleteUrl);
	}
	
	
	
	@Test
	public void testCreateInstanceActuallyCreatesAnInstance() throws Exception {
		// GIVEN the server is up


		
		UserId userId = new UserId();
		userId.setEmail("Test@gmail.com");
		userId.setDomain(this.domain);
		
		CreatedBy createdBy = new CreatedBy();
		createdBy.setUserId(userId);
		
		Location location = new Location();
		location.setLat(20.0);
		location.setLng(50.11);
		
		Map<String,Object> instanceAttributes = new HashMap<>();
		instanceAttributes.put("Key1", 100);
		instanceAttributes.put("Key2", "Test");
		instanceAttributes.put("Key3", 55.55);
		
		InstanceBoundary postInstanceBoundary = new InstanceBoundary();
		postInstanceBoundary.setInstanceId(null);
		postInstanceBoundary.setActive(true);
		postInstanceBoundary.setCreatedBy(createdBy);
		postInstanceBoundary.setLocation(location);
		postInstanceBoundary.setName("Test Instance");
		postInstanceBoundary.setType("Test Type");
		postInstanceBoundary.setInstanceAttributes(instanceAttributes);
		
		// WHEN I POST InstanceBoundary with no instanceId
		InstanceBoundary postReturnedInstanceBoundary = this.restTemplate
		.postForObject(
				this.url, 
				postInstanceBoundary, 
				InstanceBoundary.class);

		// THEN the server returns status 2xx

		GeneralId returnedInstanceId = postReturnedInstanceBoundary.getInstanceId();
		
		InstanceBoundary retrivedInstanceBoundary = this.restTemplate
				.getForObject(this.url + "/{instanceDomain}/{instanceId}", 
						InstanceBoundary.class,
						returnedInstanceId.getDomain(),
						returnedInstanceId.getId());
		
		//   AND the server created instanceId for the posted InstanceBoundary 
		assertThat(retrivedInstanceBoundary.getInstanceId())
			.isNotNull();

		//   AND the attributes of the created Instance equal to the posted InstanceBoundary   
		assertThat(retrivedInstanceBoundary.getActive())
			.isEqualTo(postInstanceBoundary.getActive());
		

		assertThat(retrivedInstanceBoundary.getName())
		.isEqualTo(postInstanceBoundary.getName());
		
		assertThat(retrivedInstanceBoundary.getLocation().getLat())
		.isEqualTo(postInstanceBoundary.getLocation().getLat());
		
		assertThat(retrivedInstanceBoundary.getLocation().getLng())
		.isEqualTo(postInstanceBoundary.getLocation().getLng());
		
		assertThat(retrivedInstanceBoundary.getType())
		.isEqualTo(postInstanceBoundary.getType());
		
		assertThat(retrivedInstanceBoundary.getCreatedBy().getUserId().getDomain())
		.isEqualTo(postInstanceBoundary.getCreatedBy().getUserId().getDomain());
		
		assertThat(retrivedInstanceBoundary.getCreatedBy().getUserId().getEmail())
		.isEqualTo(postInstanceBoundary.getCreatedBy().getUserId().getEmail());
		
		assertThat(retrivedInstanceBoundary.getInstanceAttributes()).isNotNull();
		
		assertThat(retrivedInstanceBoundary.getInstanceAttributes().get("key1"))
		.isEqualTo(postInstanceBoundary.getInstanceAttributes().get("key1"));
		
		assertThat(retrivedInstanceBoundary.getInstanceAttributes().get("key2"))
		.isEqualTo(postInstanceBoundary.getInstanceAttributes().get("key2"));
		
		assertThat(retrivedInstanceBoundary.getInstanceAttributes().get("key3"))
		.isEqualTo(postInstanceBoundary.getInstanceAttributes().get("key3"));

	}
	
	
	@Test
	public void testRetriveInstanceActuallyRetrivesTheInstance() throws Exception {
		// GIVEN the server is up
		// AND the server contains 2 instances  
		
		// instanceBoundary 1
		UserId userId1 = new UserId();
		userId1.setEmail("Test1@gmail.com");
		userId1.setDomain(this.domain);
		
		CreatedBy createdBy1 = new CreatedBy();
		createdBy1.setUserId(userId1);
		
		
		InstanceBoundary postInstanceBoundary1 = new InstanceBoundary();
		postInstanceBoundary1.setInstanceId(null);
		postInstanceBoundary1.setCreatedBy(createdBy1);
		postInstanceBoundary1.setType("Test Type");
		postInstanceBoundary1.setName("Test Name");
		postInstanceBoundary1.setActive(true);
		
		
		// instanceBoundary 2
		UserId userId2 = new UserId();
		userId2.setEmail("Test2@gmail.com");
		userId2.setDomain(this.domain);
		
		CreatedBy createdBy2 = new CreatedBy();
		createdBy2.setUserId(userId2);
		
		
		InstanceBoundary postInstanceBoundary2 = new InstanceBoundary();
		postInstanceBoundary2.setInstanceId(null);
		postInstanceBoundary2.setCreatedBy(createdBy2);
		postInstanceBoundary2.setType("Test Type");
		postInstanceBoundary2.setName("Test Name");
		postInstanceBoundary2.setActive(true);
		
		// HTTP POST
		InstanceBoundary postReturnedInstanceBoundary1 = this.restTemplate
				.postForObject(
						this.url, 
						postInstanceBoundary1, 
						InstanceBoundary.class);
		// HTTP POST
		InstanceBoundary postReturnedInstanceBoundary2 = this.restTemplate
				.postForObject(
						this.url, 
						postInstanceBoundary2, 
						InstanceBoundary.class);
		
		GeneralId returnedInstanceId1 = postReturnedInstanceBoundary1.getInstanceId();
		GeneralId returnedInstanceId2 = postReturnedInstanceBoundary2.getInstanceId();
		
		
		// WHEN I GET InstanceBoundary 1
		// AND GET InstanceBoundary 2

		
		InstanceBoundary retrivedInstanceBoundary1 = this.restTemplate
				.getForObject(this.url + "/{instanceDomain}/{instanceId}", 
						InstanceBoundary.class,
						returnedInstanceId1.getDomain(),
						returnedInstanceId1.getId());
		
		InstanceBoundary retrivedInstanceBoundary2 = this.restTemplate
				.getForObject(this.url + "/{instanceDomain}/{instanceId}", 
						InstanceBoundary.class,
						returnedInstanceId2.getDomain(),
						returnedInstanceId2.getId());

		
		
		// THEN the server returns status 2xx
		//   AND the server retrieved the 2 InstanceBoundaries 
		assertThat(retrivedInstanceBoundary1)
			.isNotNull();
		assertThat(retrivedInstanceBoundary2)
		.isNotNull();

		assertThat(retrivedInstanceBoundary1.getInstanceId().getId())
		.isNotEqualTo(retrivedInstanceBoundary2.getInstanceId().getId());
		
		//   AND the attributes of the Instance Boundaries are the correct ones   
		
		assertThat(retrivedInstanceBoundary1.getCreatedBy().getUserId().getDomain())
		.isEqualTo(postInstanceBoundary1.getCreatedBy().getUserId().getDomain());
		
		assertThat(retrivedInstanceBoundary2.getCreatedBy().getUserId().getDomain())
		.isEqualTo(postInstanceBoundary2.getCreatedBy().getUserId().getDomain());
		
		assertThat(retrivedInstanceBoundary1.getCreatedBy().getUserId().getEmail())
		.isEqualTo(postInstanceBoundary1.getCreatedBy().getUserId().getEmail());
		
		assertThat(retrivedInstanceBoundary2.getCreatedBy().getUserId().getEmail())
		.isEqualTo(postInstanceBoundary2.getCreatedBy().getUserId().getEmail());
	
	}
	
	
	
	@Test
	public void testUpdateInstanceActuallyUpdatesAnInstance() throws Exception {
		// GIVEN the server is up
	    // AND the server contains an Instance with a known id	

		
		UserId userId = new UserId();
		userId.setEmail("Test@gmail.com");
		userId.setDomain(this.domain);
		
		CreatedBy createdBy = new CreatedBy();
		createdBy.setUserId(userId);
		
		Location location = new Location();
		location.setLat(20.0);
		location.setLng(50.11);
		
		Map<String,Object> instanceAttributes = new HashMap<>();
		instanceAttributes.put("Key1", 100);
		instanceAttributes.put("Key2", "Test");
		instanceAttributes.put("Key3", 55.55);
		
		InstanceBoundary postInstanceBoundary = new InstanceBoundary();
		postInstanceBoundary.setInstanceId(null);
		postInstanceBoundary.setActive(true);
		postInstanceBoundary.setCreatedBy(createdBy);
		postInstanceBoundary.setLocation(location);
		postInstanceBoundary.setName("Test Instance");
		postInstanceBoundary.setType("Test Type");
		postInstanceBoundary.setInstanceAttributes(instanceAttributes);
		
		// HTTP POST
		InstanceBoundary postReturnedInstanceBoundary = this.restTemplate
		.postForObject(
				this.url, 
				postInstanceBoundary, 
				InstanceBoundary.class);
		
		
		//initialize update instanceBoundary
		InstanceBoundary update = new InstanceBoundary();
		update.setName("updated Instance name");
		update.setType("updated Instance type");
		
		Map<String,Object> updatedInstanceAttributes = new HashMap<>();
		instanceAttributes.put("Key1", 15);
		instanceAttributes.put("Key2", "update Test");
		instanceAttributes.put("Key3", 11.11);
		update.setInstanceAttributes(updatedInstanceAttributes);
		
		Location updatedLocation = new Location();
		updatedLocation.setLat(3.5);
		updatedLocation.setLng(5.5);
		update.setLocation(updatedLocation);
		
		// WHEN I PUT InstanceBoundary update
		this.restTemplate
			.put(this.url + "/{instanceDomain}/{instanceId}",
					update,
					postReturnedInstanceBoundary.getInstanceId().getDomain(), 
					postReturnedInstanceBoundary.getInstanceId().getId());

		// THEN the server returns status 2xx

		
		InstanceBoundary updatedInstanceBoundary = this.restTemplate
				.getForObject(this.url + "/{instanceDomain}/{instanceId}", 
						InstanceBoundary.class,
						postReturnedInstanceBoundary.getInstanceId().getDomain(),
						postReturnedInstanceBoundary.getInstanceId().getId());
		
		//   AND the relevant attributes updated 
		assertThat(updatedInstanceBoundary.getName())
			.isNotEqualTo(postInstanceBoundary.getName());
		
		assertThat(updatedInstanceBoundary.getType())
		.isNotEqualTo(postInstanceBoundary.getType());
		
		assertThat(updatedInstanceBoundary.getLocation().getLat())
		.isNotEqualTo(postInstanceBoundary.getLocation().getLat());
		
		assertThat(updatedInstanceBoundary.getLocation().getLng())
		.isNotEqualTo(postInstanceBoundary.getLocation().getLng());
		
		assertThat(updatedInstanceBoundary.getInstanceAttributes()).isNotNull();
		
		assertThat(updatedInstanceBoundary.getInstanceAttributes().get("Key1"))
		.isNotEqualTo(postInstanceBoundary.getInstanceAttributes().get("Key1"));
		
		assertThat(updatedInstanceBoundary.getInstanceAttributes().get("Key2"))
		.isNotEqualTo(postInstanceBoundary.getInstanceAttributes().get("Key2"));
		
		
		assertThat(updatedInstanceBoundary.getInstanceAttributes().get("Key3"))
		.isNotEqualTo(postInstanceBoundary.getInstanceAttributes().get("Key3"));

		//   AND the attributes that not updated stayed the same 
		assertThat(updatedInstanceBoundary.getActive())
			.isEqualTo(postInstanceBoundary.getActive());
		
		assertThat(updatedInstanceBoundary.getCreatedBy().getUserId().getDomain())
		.isEqualTo(postInstanceBoundary.getCreatedBy().getUserId().getDomain());
		
		assertThat(updatedInstanceBoundary.getCreatedBy().getUserId().getEmail())
		.isEqualTo(postInstanceBoundary.getCreatedBy().getUserId().getEmail());
		
		
		assertThat(updatedInstanceBoundary.getInstanceId().getDomain())
		.isEqualTo(postReturnedInstanceBoundary.getInstanceId().getDomain());
		
		assertThat(updatedInstanceBoundary.getInstanceId().getId())
		.isEqualTo(postReturnedInstanceBoundary.getInstanceId().getId());
		
		assertThat(updatedInstanceBoundary.getCreatedTimestamp())
		.isEqualTo(postReturnedInstanceBoundary.getCreatedTimestamp());
		

		


	}
	
	
	
	@Test
	public void testGAllInstanceActuallyGetsAllTheInstances() throws Exception {
		// GIVEN the server is up
		// AND the server contains 3 instances  
		
		// instanceBoundary 1
		UserId userId1 = new UserId();
		userId1.setEmail("Test1@gmail.com");
		userId1.setDomain(this.domain);
		
		CreatedBy createdBy1 = new CreatedBy();
		createdBy1.setUserId(userId1);
		
		
		InstanceBoundary postInstanceBoundary1 = new InstanceBoundary();
		postInstanceBoundary1.setInstanceId(null);
		postInstanceBoundary1.setCreatedBy(createdBy1);
		postInstanceBoundary1.setType("Test Type");
		postInstanceBoundary1.setName("Test Name");
		postInstanceBoundary1.setActive(true);
		
		
		// instanceBoundary 2
		UserId userId2 = new UserId();
		userId2.setEmail("Test2@gmail.com");
		userId2.setDomain(this.domain);
		
		CreatedBy createdBy2 = new CreatedBy();
		createdBy2.setUserId(userId2);
		
		
		InstanceBoundary postInstanceBoundary2 = new InstanceBoundary();
		postInstanceBoundary2.setInstanceId(null);
		postInstanceBoundary2.setCreatedBy(createdBy2);
		postInstanceBoundary2.setType("Test Type");
		postInstanceBoundary2.setName("Test Name");
		postInstanceBoundary2.setActive(true);
		
		
		// instanceBoundary 3
		UserId userId3 = new UserId();
		userId3.setEmail("Test3@gmail.com");
		userId3.setDomain(this.domain);
		
		CreatedBy createdBy3 = new CreatedBy();
		createdBy3.setUserId(userId3);
		
		
		InstanceBoundary postInstanceBoundary3 = new InstanceBoundary();
		postInstanceBoundary3.setInstanceId(null);
		postInstanceBoundary3.setCreatedBy(createdBy3);
		postInstanceBoundary3.setType("Test Type");
		postInstanceBoundary3.setName("Test Name");
		postInstanceBoundary3.setActive(true);
		
		//InstanceBoundary Array
		InstanceBoundary[] createdInstanceBoundaryArray  = new InstanceBoundary[] {postInstanceBoundary1,postInstanceBoundary2,postInstanceBoundary3};
		
		
		// HTTP POST
		InstanceBoundary postReturnedInstanceBoundary1 = this.restTemplate
				.postForObject(
						this.url, 
						postInstanceBoundary1, 
						InstanceBoundary.class);
		// HTTP POST
		InstanceBoundary postReturnedInstanceBoundary2 = this.restTemplate
				.postForObject(
						this.url, 
						postInstanceBoundary2, 
						InstanceBoundary.class);
		
		
		// HTTP POST
		InstanceBoundary postReturnedInstanceBoundary3 = this.restTemplate
				.postForObject(
						this.url, 
						postInstanceBoundary3, 
						InstanceBoundary.class);
		

		//InstanceBoundary Array
		InstanceBoundary[] postReturnedInstanceBoundaryArray  = new InstanceBoundary[] {postReturnedInstanceBoundary1,postReturnedInstanceBoundary2,postReturnedInstanceBoundary3};
		
		// WHEN I GET all Instances
		InstanceBoundary[] retrivedInstanceBoundaryArray = this.restTemplate
				.getForObject(this.url, 
						InstanceBoundary[].class);
		
		
		// THEN the server returns status 2xx
		//   AND the server retrieved the  InstanceBoundariesArray 
		assertThat(retrivedInstanceBoundaryArray)
			.isNotNull();
		
		//   AND all of the instances retrieved
		
		for(int i = 0 ; i < createdInstanceBoundaryArray.length ; i++) {
			
			assertThat(retrivedInstanceBoundaryArray[i].getCreatedBy().getUserId().getDomain())
			.isEqualTo(createdInstanceBoundaryArray[i].getCreatedBy().getUserId().getDomain());
			
			assertThat(retrivedInstanceBoundaryArray[i].getCreatedBy().getUserId().getEmail())
			.isEqualTo(createdInstanceBoundaryArray[i].getCreatedBy().getUserId().getEmail());
			
			assertThat(retrivedInstanceBoundaryArray[i].getInstanceId().getDomain())
			.isEqualTo(postReturnedInstanceBoundaryArray[i].getInstanceId().getDomain());
			
			assertThat(retrivedInstanceBoundaryArray[i].getInstanceId().getId())
			.isEqualTo(postReturnedInstanceBoundaryArray[i].getInstanceId().getId());
		}
		
	}
	
}
