package iob.service.mockups;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import iob.bounderies.UserBoundary;
import iob.bounderies.UserId;
import iob.data.UserEntity;
import iob.data.UserRole;
import iob.logic.UserConverter;
import iob.logic.UsersService;

@Service
public class UsersServiceMockup implements UsersService{

	Vector<UserEntity> userEntitiesVector; 
	private UserConverter userConverter;
	
	@Autowired
	public UsersServiceMockup() {
		userEntitiesVector = new Vector<>();
		userConverter= new UserConverter();
	}
	
	@Override
	public UserBoundary createUser(UserBoundary user) {
		
		if(user == null) {
			throw new RuntimeException("User is NULL.");
		}
		if(user.getUserId() == null || user.getUserId().equals(""))
		{
			throw new RuntimeException("User domain AND user email are NULL or empty.");
		}
		if(user.getRole() == null || user.getRole().equals("")) {
			throw new RuntimeException("User Role is Null or empty.");
		}
		if(!user.getRole().equalsIgnoreCase("PLAYER")&&
				!user.getRole().equalsIgnoreCase("MANAGER")&&
				!user.getRole().equalsIgnoreCase("ADMIN")) {
			throw new RuntimeException("User Role is not valid.");
		}
		if(user.getUsername() == null|| user.getUsername().equals(""))  {
			throw new RuntimeException("User name is NULL or empty.");
		}
		
		if(user.getAvatar() == null || user.getAvatar().equals(""))  {
			throw new RuntimeException("User avatar is NULL or empty.");
		}
		
		UserEntity userEntity = userConverter.toEntity(user);
		userEntitiesVector.add(userEntity);
		
		return user;
	}

	@Override
	public UserBoundary login(String userDomain, String userEmail) {
		
		if(userDomain == null || userDomain.equals("")) {
			throw new RuntimeException("User domain is NULL or empty.");
		}
		if(userEmail == null || userEmail.equals("")) {
			throw new RuntimeException("User Email is NULL or empty.");
		}
		
		UserId userId = new UserId();
				userId.setDomain(userDomain);
				userId.setEmail(userEmail);
		
		
		UserBoundary userBoundary = new UserBoundary();
				userBoundary.setUserId(userId);
				userBoundary.setRole("Manager");
				userBoundary.setUsername("Demo User");
				userBoundary.setAvatar("J");
		
		return userBoundary;
	}

	@Override
	public UserBoundary updateUser(String userdomain, String userEmail, UserBoundary update) {
		
		
		if(userdomain == null || userdomain.equals("")) {
			throw new RuntimeException("User Domain is NULL or empty.");
		}
		if(userEmail == null || userEmail.equals("")) {
			throw new RuntimeException("User Email is NULL or empty.");
		}
		if(update == null) {
			throw new RuntimeException("User is NULL.");
		}
		if(update.getAvatar() == null || update.getAvatar().equals("")) {
			throw new RuntimeException("Avatar is NULL or empty.");
		}
		if(update.getUsername() == null || update.getUsername().equals("")) {
			throw new RuntimeException("Name is NULL or empty.");
		}
		if(update.getRole() == null || update.getRole().equals("")) {
			throw new RuntimeException("Role is NULL or empty.");
		}
		
		
		for (int i = 0; i < userEntitiesVector.size(); i++) {
			if(userEntitiesVector.get(i).getUserDomain().equals(userdomain)&&
					(userEntitiesVector.get(i).getUserEmail().equals(userEmail))) {
				userEntitiesVector.get(i).setAvatar(update.getAvatar());
				userEntitiesVector.get(i).setUsername(update.getUsername());
				userEntitiesVector.get(i).setRole(UserRole.valueOf(update.getRole()));
				return userConverter.toBoundary(userEntitiesVector.get(i));
			}
		}
		
		throw new RuntimeException("User not found, user domain and user email does not exist.");
	}

	@Override
	public List<UserBoundary> getAllUsers() {
		return userEntitiesVector 
				.stream()
				.map(userConverter::toBoundary) 
				.collect(Collectors.toList()); 
	}

	@Override
	public void deleteAllUsers() {
		userEntitiesVector.clear();
	}

}


