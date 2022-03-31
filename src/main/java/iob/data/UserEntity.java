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



	public void setUserDomain(String userDomain) {
		this.userDomain = userDomain;
		
	}



	public String getUserEmail() {
		return userEmail;
	}



	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
		
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
