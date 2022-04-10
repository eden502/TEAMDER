package iob.logic;

import org.springframework.stereotype.Component;

import iob.bounderies.NewUserBoundary;
import iob.bounderies.UserBoundary;
import iob.bounderies.UserId;
import iob.data.UserEntity;
import iob.data.UserRole;

@Component
public class UserConverter {

	public UserBoundary toBoundary(UserEntity entity) {

		UserId userId = new UserId();
		String boundaryDomain = getDomainFromId(entity.getId());
	    String boundaryEmail = getEmailFromId(entity.getId());
		userId.setDomain(boundaryDomain);
		userId.setEmail(boundaryEmail);
		UserBoundary userBoundary = new UserBoundary();
		userBoundary.setUserId(userId);
		userBoundary.setAvatar(entity.getAvatar());
		userBoundary.setRole(entity.getRole().name());
		userBoundary.setUsername(entity.getUsername());
		return userBoundary;
	}

	public UserEntity toEntity(UserBoundary boundary) {

		UserEntity userEntity = new UserEntity();
		userEntity.setId(boundary.getUserId().getDomain()+"@"+boundary.getUserId().getEmail());
		userEntity.setUsername(boundary.getUsername());
		userEntity.setRole(UserRole.valueOf(boundary.getRole().toUpperCase()));
		userEntity.setAvatar(boundary.getAvatar());
		return userEntity;

	}

	public UserBoundary newUserbToUserb(NewUserBoundary newUserB) {

		UserId userId = new UserId();
		userId.setDomain(null);
		userId.setEmail(newUserB.getEmail());

		UserBoundary userBoundary = new UserBoundary();
		userBoundary.setUserId(userId);
		userBoundary.setRole(newUserB.getRole());
		userBoundary.setUsername(newUserB.getUsername());
		userBoundary.setAvatar(newUserB.getAvatar());

		return userBoundary;

	}
	
	public String getDomainFromId(String id) {
		String[] splited_domain_email = id.split("@", 2);
		return splited_domain_email[0];
		
	}
	
	public String getEmailFromId(String id) {
		String[] splited_domain_email = id.split("@", 2);
		return splited_domain_email[1];
		
	}
}
