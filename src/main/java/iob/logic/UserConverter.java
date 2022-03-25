package iob.logic;
import org.springframework.stereotype.Component;

import bounderies.UserBoundary;
import bounderies.UserId;
import iob.data.UserEntity;
import iob.data.UserRole;

@Component
public class UserConverter {

	public UserBoundary toBoundary(UserEntity entity) {
		
		UserId userId = new UserId()
				.setDomain(entity.getUserDomain())
				.setEmail(entity.getUserEmail());
		UserBoundary userBoundary = new UserBoundary()
				.setUserId(userId)
				.setAvatar(entity.getAvatar())
				.setRole(entity.getRole().name())
				.setUsername(entity.getUsername());
		return userBoundary;
	}
	
	public UserEntity toEntity(UserBoundary boundary) {
		
		UserEntity userEntity = new UserEntity()
				.setUserDomain(boundary.getUserId().getDomain())
				.setUserEmail(boundary.getUserId().getEmail())
				.setUsername(boundary.getUsername())
				.setRole(UserRole.valueOf(boundary.getRole()))
				.setAvatar(boundary.getAvatar());
		return userEntity;
		
	}
}
