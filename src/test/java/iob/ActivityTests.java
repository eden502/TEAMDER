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

import iob.bounderies.ActivityBoundary;
import iob.bounderies.GeneralId;
import iob.bounderies.Instance;
import iob.bounderies.InvokedBy;
import iob.bounderies.UserId;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ActivityTests {

	private int port;
	private String url;
	private String adminUrl;
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
		this.url = "http://localhost:" + this.port + "/iob/activities";
		this.adminUrl = "http://localhost:" + this.port + "/iob/admin/activities";
		this.restTemplate = new RestTemplate();
	}
	

	@AfterEach
	public void tearDown() {
		this.restTemplate
			.delete(this.adminUrl);
	}
	
	
	@Test
	public void testInvokeInstanceActivityActuallyInvokesAnInstanceActivity() throws Exception {
		// GIVEN the server is up
		
		//initialize instance with instanceId
		GeneralId instanceId = new GeneralId();
		instanceId.setDomain(this.domain);
		instanceId.setId("Test ID");
		Instance instance = new Instance();
		instance.setInstanceId(instanceId);
		
		//initialize InvokedBy with UserId
		UserId userId = new UserId();
		userId.setDomain(this.domain);
		userId.setEmail("Test@gmail.com");
		InvokedBy invokedBy = new InvokedBy();
		invokedBy.setUserId(userId);
		
		//initialize ActivityBoundary with UserId and Instance
		Map<String,Object> activityAttributes = new HashMap<>();
		activityAttributes.put("Key1", 100);
		activityAttributes.put("Key2", "Test");
		activityAttributes.put("Key3", 55.55);
		
		ActivityBoundary postActivityBoundary = new ActivityBoundary();
		postActivityBoundary.setInstance(instance);
		postActivityBoundary.setInvokedBy(invokedBy);
		postActivityBoundary.setActivityAttributes(activityAttributes);
		postActivityBoundary.setType("Test Type");
		
		// WHEN I POST ActivityBoundary with null activityId
		// POST the ActivityBoundary
		this.restTemplate.postForObject(
				this.url,
				postActivityBoundary,
				ActivityBoundary.class);

		// THEN the server returns status 2xx
		// GET the Object (ActivityBoundary)
		ActivityBoundary[] activityBoundariesArray = this.restTemplate.getForObject(
				adminUrl,
				ActivityBoundary[].class);	
		
		// AND the server created activityId for the posted ActivityBoundary 
		// check that activityId created
		assertThat(activityBoundariesArray[0]).isNotNull();
		assertThat(activityBoundariesArray[0].getActivityId()).isNotNull();

		// AND the attributes of the ActivityBoundary retrieved equal to the posted ActivityBoundary
		// check that attributes are the same
		assertThat(activityBoundariesArray[0].getInstance().getInstanceId().getId())
		.isEqualTo(postActivityBoundary.getInstance().getInstanceId().getId());
		
		assertThat(activityBoundariesArray[0].getInstance().getInstanceId().getDomain())
		.isEqualTo(postActivityBoundary.getInstance().getInstanceId().getDomain());
		
		assertThat(activityBoundariesArray[0].getInvokedBy().getUserId().getDomain())
		.isEqualTo(postActivityBoundary.getInvokedBy().getUserId().getDomain());
		
		assertThat(activityBoundariesArray[0].getInvokedBy().getUserId().getEmail())
		.isEqualTo(postActivityBoundary.getInvokedBy().getUserId().getEmail());

		assertThat(activityBoundariesArray[0].getType())
		.isEqualTo(postActivityBoundary.getType());
		
		assertThat(activityBoundariesArray[0].getActivityAttributes().get("Key1"))
		.isEqualTo(postActivityBoundary.getActivityAttributes().get("Key1"));
		
		assertThat(activityBoundariesArray[0].getActivityAttributes().get("Key2"))
		.isEqualTo(postActivityBoundary.getActivityAttributes().get("Key2"));
		
		assertThat(activityBoundariesArray[0].getActivityAttributes().get("Key3"))
		.isEqualTo(postActivityBoundary.getActivityAttributes().get("Key3"));

	}
}
