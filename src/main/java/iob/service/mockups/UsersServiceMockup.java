package iob.service.mockups;

import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import bounderies.UserBoundary;
import iob.data.UserEntity;
import iob.logic.UsersService;

@Service
public class UsersServiceMockup implements UsersService{

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllUsers() {
		// TODO Auto-generated method stub
		
	}

}
