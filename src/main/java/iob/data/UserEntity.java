package iob.data;

public class UserEntity {
	
	private String userDomain;
	private String userEmail;
	private UserRole role;
	private String username;
	private String avatar;
	
	
	public UserEntity() {
		
	}



	public String getUserDomain() {
		return userDomain;
	}



	public UserEntity setUserDomain(String userDomain) {
		this.userDomain = userDomain;
		return this;
	}



	public String getUserEmail() {
		return userEmail;
	}



	public UserEntity setUserEmail(String userEmail) {
		this.userEmail = userEmail;
		return this;
	}

	


	public UserRole getRole() {
		return role;
	}



	public UserEntity setRole(UserRole role) {
		this.role = role;
		return this;
	}



	public String getUsername() {
		return username;
	}


	public UserEntity setUsername(String username) {
		this.username = username;
		return this;
	}


	public String getAvatar() {
		return avatar;
	}


	public UserEntity setAvatar(String avatar) {
		this.avatar = avatar;
		return this;
	}
	
}
