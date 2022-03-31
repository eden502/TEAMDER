package iob.service.mockups;

import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import iob.bounderies.UserBoundary;
import iob.data.UserEntity;
import iob.logic.UserConverter;
import iob.logic.UsersService;

@Service
public class UsersServiceMockup implements UsersService{

	private UserConverter userConverter;
	
	Vector<UserEntity> v = new Vector<UserEntity>();
	
	@Autowired
	public UsersServiceMockup() {
		
	}
	
	@Override
	public UserBoundary createUser(UserBoundary user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserBoundary login(String userDomain, String userEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserBoundary updateUser(String userdomain, String userEmail, UserBoundary update) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserBoundary> getAllUsers() {
		return v // Vector<UserEntity>
				.stream() // Stream <UserEntity>
				.map(userConverter::toBoundary) // Stream <UserBoundary> using Method Reference
				.collect(Collectors.toList()); // List<UserBoundary>
	}

	@Override
	public void deleteAllUsers() {
		// TODO Auto-generated method stub
		
	}

}
