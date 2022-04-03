package iob.service.mockups;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

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

	private List<UserEntity> userEntiiesList;  
	private UserConverter userConverter;
	
	@Autowired
	public UsersServiceMockup() {
		userConverter= new UserConverter();
	}
	
	@PostConstruct
	public void init () {
		// create a thread safe list
		this.userEntiiesList = Collections.synchronizedList(new ArrayList<>()); 
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
		userEntiiesList.add(userEntity);
		
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
		
		for (int i = 0; i < userEntiiesList.size(); i++) {
			if(userEntiiesList.get(i).getUserDomain().equals(userDomain)&&
					(userEntiiesList.get(i).getUserEmail().equals(userEmail))) {
				return userConverter.toBoundary(userEntiiesList.get(i));
			}
		}

			throw new RuntimeException("User not found, kindly sign up first.");
		
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
		
		
		for (int i = 0; i < userEntiiesList.size(); i++) {
			if(userEntiiesList.get(i).getUserDomain().equals(userdomain)&&
					(userEntiiesList.get(i).getUserEmail().equals(userEmail))) {
				userEntiiesList.get(i).setAvatar(update.getAvatar());
				userEntiiesList.get(i).setUsername(update.getUsername());
				userEntiiesList.get(i).setRole(UserRole.valueOf(update.getRole().toUpperCase()));
				return userConverter.toBoundary(userEntiiesList.get(i));
			}
		}
		
		throw new RuntimeException("User not found, user domain and user email does not exist.");
	}

	@Override
	public List<UserBoundary> getAllUsers() {
		return userEntiiesList 
				.stream()
				.map(userConverter::toBoundary) 
				.collect(Collectors.toList()); 
	}

	@Override
	public void deleteAllUsers() {
		userEntiiesList.clear();
	}

}


