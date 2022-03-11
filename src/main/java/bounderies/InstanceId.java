package bounderies;

public class InstanceId {

	private String domain;
	private String id;
	
	
	public InstanceId() {
		super();
	}


	public String getDomain() {
		return domain;
	}


	public InstanceId setDomain(String domain) {
		this.domain = domain;
		return this;
	}


	public String getId() {
		return id;
	}


	public InstanceId setId(String id) {
		this.id = id;
		return this;
	}
	
}
