package iob.logic;

import java.util.List;

import iob.bounderies.UserBoundary;

public interface UsersServiceEnhanced extends UsersService  {
	
	
	public void deleteAllUsers(String domain, String email);

}
