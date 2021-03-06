package iob.service.dao;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;


import iob.bounderies.UserBoundary;

import iob.data.UserEntity;
import iob.data.UserRole;
import iob.exceptions.NoPermissionException;
import iob.exceptions.NotFoundException;
import iob.logic.IdConverter;
import iob.logic.UserConverter;

import iob.logic.UsersServiceEnhanced;

@Service
public class UsersServiceMongo implements UsersServiceEnhanced {
	private UserDao userDao;
	private UserConverter userConverter;
	private IdConverter idConverter;
	private String domain;

	@Autowired
	public UsersServiceMongo(UserDao userDao, UserConverter userConverter,IdConverter idConverter) {
		this.userDao = userDao;
		this.userConverter = userConverter;
		this.idConverter = idConverter;
	}

	@Value("${spring.application.name:null}")
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public UserBoundary createUser(UserBoundary userBoundary) {

		
		userBoundary.getUserId().setDomain(this.domain);
		
		validateUserBoundary(userBoundary);

		

		UserEntity userEntity = userConverter.toEntity(userBoundary);
		userEntity = userDao.save(userEntity);
		userBoundary = userConverter.toBoundary(userEntity);
		return userBoundary;
	}

	@Override
	public UserBoundary login(String userDomain, String userEmail) {
		
		
		validateUserId(userEmail,userDomain);

		UserEntity userEntity = getUserEntityById(userEmail,userDomain);

		String userEntityDomain = this.idConverter.getUserDomainFromUserEntityId(userEntity.getId());
		String userEntityEmail = this.idConverter.getUserEmailFromUserEntityId(userEntity.getId());
		
		if (userEntityDomain.equals(userDomain) && userEntityEmail.equals(userEmail))
			return userConverter.toBoundary(userEntity);
		else
			throw new RuntimeException("Email or domain were not found.");
	}

	@Override
	public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update) {
		
		UserEntity userEntity = this.getUserEntityById(userEmail,userDomain);

		if (update.getAvatar() != null) {
			userEntity.setAvatar(update.getAvatar());
		}
		if (update.getUsername() != null) {
			userEntity.setUsername(update.getUsername());

		}
		if (update.getRole() != null) {
			userEntity.setRole(UserRole.valueOf(update.getRole().toUpperCase()));
		}

		userEntity = this.userDao.save(userEntity);
		return this.userConverter.toBoundary(userEntity);
	}

	@Override
	@Deprecated
	public List<UserBoundary> getAllUsers() {
		
		//Iterable<UserEntity> usersIterable = this.userDao.findAll();
		//int page= 1;
		//int size = 1;
		//return this.userDao
		//		.findAll(PageRequest.of(page, size, Direction.ASC, "username", "id"))
		//		.getContent()
		//		.stream()
		//		.map(this.userConverter::toBoundary)
		//		.collect(Collectors.toList());
		//Stream<UserEntity> stream = StreamSupport.stream(usersIterable.spliterator(), false);
		//return stream.map(userConverter::toBoundary).collect(Collectors.toList());
		throw new RuntimeException("Deprecated Method");
	}
	
	@Override
	public List<UserBoundary> getAllUsers(String userDomain,String userEmail,int size, int page) {
		UserEntity userEntity = getUserEntityById(userEmail, userDomain);
		
		if(userEntity.getRole() != UserRole.ADMIN)
			throw new NoPermissionException();
		
		return this.userDao.findAll(PageRequest.of(page, size,Direction.DESC,"role"))
				.getContent()
				.stream()
				.map(this.userConverter::toBoundary)
				.collect(Collectors.toList());
	}

	@Override
	@Deprecated
	public void deleteAllUsers() {
		//this.userDao.deleteAll();
		throw new RuntimeException("Deprecated Method");
	}
	@Override
	public void deleteAllUsers(String domain, String email) {
		UserEntity ue = getUserEntityById(email, domain);
		if (ue.getRole() != UserRole.ADMIN)
			throw new NoPermissionException();
		this.userDao.deleteAll();
	}

	private UserEntity getUserEntityById(String userEmail,String userDomain) {
		String id = idConverter.getUserEntityIdFromDomainAndEmail(userDomain, userEmail);
		Optional<UserEntity> optional = this.userDao
				.findById(id);

		if (optional.isPresent()) {
			UserEntity userEntity = optional.get();
			return userEntity;
		} else {
			throw new NotFoundException("Cannot find user with id: " + id);
		}
	}

	private boolean validateEmail(String email) {
		final String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	private void  validateUserId(String userEmail,String userDomain) {


		if (userEmail == null || userEmail.trim().isEmpty()
				|| !validateEmail(userEmail)) {
			throw new RuntimeException("User email is not valid.");
		}
		if (userDomain == null || userDomain.trim().isEmpty()
				|| !userDomain.equals(this.domain)) {
			throw new RuntimeException("User domain is not valid.");
		}
	}

	private void validateUserBoundary(UserBoundary userBoundary) {
		if (userBoundary == null) {
			throw new RuntimeException("User is NULL.");
		}
		
		
		validateUserId(userBoundary.getUserId().getEmail(),userBoundary.getUserId().getDomain());
		validateUserRole(userBoundary.getRole());

		if (userBoundary.getUsername() == null || userBoundary.getUsername().trim().isEmpty()) {
			throw new RuntimeException("User name is NULL or empty.");
		}

		if (userBoundary.getAvatar() == null || userBoundary.getAvatar().trim().isEmpty()) {
			throw new RuntimeException("User avatar is NULL or empty.");
		}

	}

	private void validateUserRole(String role) {
		if (role == null || role.trim().isEmpty()) {
			throw new RuntimeException("User Role is Null or empty.");
		}
		if (!role.equalsIgnoreCase(UserRole.PLAYER.toString()) && !role.equalsIgnoreCase(UserRole.MANAGER.toString())
				&& !role.equalsIgnoreCase(UserRole.ADMIN.toString())) {
			throw new RuntimeException("User Role is not valid.");
		}
		
	}




}
