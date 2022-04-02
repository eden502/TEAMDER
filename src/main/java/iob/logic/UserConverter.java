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
				userId.setDomain(entity.getUserDomain());
				userId.setEmail(entity.getUserEmail());
		UserBoundary userBoundary = new UserBoundary();
				userBoundary.setUserId(userId);
				userBoundary.setAvatar(entity.getAvatar());
				userBoundary.setRole(entity.getRole().name());
				userBoundary.setUsername(entity.getUsername());
		return userBoundary;
	}
	
	public UserEntity toEntity(UserBoundary boundary) {
		
		UserEntity userEntity = new UserEntity();
				userEntity.setUserDomain(boundary.getUserId().getDomain());
				userEntity.setUserEmail(boundary.getUserId().getEmail());
				userEntity.setUsername(boundary.getUsername());
				userEntity.setRole(UserRole.valueOf(boundary.getRole()));
				userEntity.setAvatar(boundary.getAvatar());
		return userEntity;
		
	}
	public UserBoundary newUserbToUserb(NewUserBoundary newUserB) {
		
		UserId userId = new UserId();
				userId.setDomain("2022b.diana.ukrainsky");
				userId.setEmail(newUserB.getEmail());
		
		UserBoundary userBoundary = new UserBoundary();
				userBoundary.setUserId(userId);
				userBoundary.setRole(newUserB.getRole());
				userBoundary.setUsername(newUserB.getUsername());
				userBoundary.setAvatar(newUserB.getAvatar());
		
		
		return userBoundary;
		
		
		
	}
}

