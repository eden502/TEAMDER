package iob.service.mockups;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import iob.bounderies.UserBoundary;
import iob.bounderies.UserId;
import iob.data.UserEntity;
import iob.data.UserRole;
import iob.logic.UserConverter;
import iob.logic.UsersService;

@Service
public class UsersServiceMockup implements UsersService{

	private List<UserEntity> userEntitiesList;  
	private UserConverter userConverter;
	private String domain;
	
	@Autowired
	public UsersServiceMockup() {
		userConverter= new UserConverter();
	}
	
	@Value("${spring.application.name:null}")
	public void setDomain(String domain) {
		this.domain = domain;
		System.err.println("Domain in users = " + this.domain);
	}
	
	@PostConstruct
	public void init () {
		// create a thread safe list
		this.userEntitiesList = Collections.synchronizedList(new ArrayList<>()); 
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
		
		user.getUserId().setDomain(this.domain);
		
		UserEntity userEntity = userConverter.toEntity(user);
		userEntitiesList.add(userEntity);
		
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
		
		for (int i = 0; i < userEntitiesList.size(); i++) {
			if(userEntitiesList.get(i).getUserDomain().equals(userDomain)&&
					(userEntitiesList.get(i).getUserEmail().equals(userEmail))) {
				return userConverter.toBoundary(userEntitiesList.get(i));
			}
		}

			throw new RuntimeException("User not found, kindly sign up first.");
		
	}

	@Override
	public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update) {
		
		
		if(userDomain == null || userDomain.equals("")) {
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
		
		
		for (int i = 0; i < userEntitiesList.size(); i++) {
			if(userEntitiesList.get(i).getUserDomain().equals(userDomain)&&
					(userEntitiesList.get(i).getUserEmail().equals(userEmail))) {
				userEntitiesList.get(i).setAvatar(update.getAvatar());
				userEntitiesList.get(i).setUsername(update.getUsername());
				userEntitiesList.get(i).setRole(UserRole.valueOf(update.getRole().toUpperCase()));
				return userConverter.toBoundary(userEntitiesList.get(i));
			}
		}
		
		throw new RuntimeException("User not found, user domain and user email does not exist.");
	}

	@Override
	public List<UserBoundary> getAllUsers() {
		return userEntitiesList 
				.stream()
				.map(userConverter::toBoundary) 
				.collect(Collectors.toList()); 
	}

	@Override
	public void deleteAllUsers() {
		userEntitiesList.clear();
	}

}


