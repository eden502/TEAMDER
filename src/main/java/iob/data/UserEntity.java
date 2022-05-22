package iob.data;


import javax.persistence.Id;


import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserEntity {
	
	private String id;
	private UserRole role;
	private String username;
	private String avatar;
	
	public UserEntity() {}
	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
		
	}

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
		
	}


	public String getAvatar() {
		return avatar;
	}


	public void setAvatar(String avatar) {
		this.avatar = avatar;
		
	}
	
}
