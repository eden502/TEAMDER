package iob.service.dao;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iob.bounderies.UserBoundary;
import iob.data.UserEntity;
import iob.data.UserRole;
import iob.logic.UserConverter;
import iob.logic.UsersService;

@Service
public class UsersServiceJpa implements UsersService {
	private UserDao userDao;
	private UserConverter userConverter;
	private String domain;

	@Autowired
	public UsersServiceJpa(UserDao userDao, UserConverter userConverter) {
		this.userDao = userDao;
		this.userConverter = userConverter;
	}

	@Value("${spring.application.name:null}")
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	@Transactional
	public UserBoundary createUser(UserBoundary userBoundary) {

		if (userBoundary == null) {
			throw new RuntimeException("User is NULL.");
		}
		if (userBoundary.getUserId() == null) {
			throw new RuntimeException("User Id is NULL.");
		}

		if (userBoundary.getUserId().getEmail() == null || userBoundary.getUserId().getEmail().trim().isEmpty()||!validateEmail(userBoundary.getUserId().getEmail())) {
			throw new RuntimeException("User email is not valid.");
		}

		if (userBoundary.getRole() == null || userBoundary.getRole().equals("")) {
			throw new RuntimeException("User Role is Null or empty.");
		}
		if (!userBoundary.getRole().equalsIgnoreCase("PLAYER") && !userBoundary.getRole().equalsIgnoreCase("MANAGER")
				&& !userBoundary.getRole().equalsIgnoreCase("ADMIN")) {
			throw new RuntimeException("User Role is not valid.");
		}
		if (userBoundary.getUsername() == null || userBoundary.getUsername().equals("")) {
			throw new RuntimeException("User name is NULL or empty.");
		}

		if (userBoundary.getAvatar() == null || userBoundary.getAvatar().equals("")) {
			throw new RuntimeException("User avatar is NULL or empty.");
		}

		userBoundary.getUserId().setDomain(this.domain);

		UserEntity userEntity = userConverter.toEntity(userBoundary);
		userEntity = userDao.save(userEntity);
		userBoundary = userConverter.toBoundary(userEntity);
		return userBoundary;
	}
	
	private boolean validateEmail(String email) {
		final String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	@Override
	@Transactional(readOnly = true)
	public UserBoundary login(String userDomain, String userEmail) {

		if (userDomain == null || userDomain.equals("")) {
			throw new RuntimeException("User domain is NULL or empty.");
		}
		if (userEmail == null || userEmail.equals("")) {
			throw new RuntimeException("User Email is NULL or empty.");
		}

		UserEntity userEntity = getUserEntityById(userDomain, userEmail);

		String userDomain_splited = this.userConverter.getUserDomainFromUserEntityId(userEntity.getId());

		if (userDomain_splited.equals(userDomain))
			return userConverter.toBoundary(userEntity);
		else
			throw new RuntimeException("Email or domain are not valid.");
	}

	@Override
	@Transactional
	public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update) {

		UserEntity userEntity = this.getUserEntityById(userDomain, userEmail);

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
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers() {

		Iterable<UserEntity> usersIterable = this.userDao.findAll();
		Stream<UserEntity> stream = StreamSupport.stream(usersIterable.spliterator(), false);
		return stream.map(userConverter::toBoundary).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteAllUsers() {
		this.userDao.deleteAll();
	}

	private UserEntity getUserEntityById(String userDomain, String userEmail) {

		Optional<UserEntity> optional = this.userDao
				.findById(userConverter.getUserEntityIdFromDomainAndEmail(userDomain, userEmail));

		if (optional.isPresent()) {
			UserEntity userEntity = optional.get();
			return userEntity;
		} else {
			throw new RuntimeException("Cannot find user with id: " + userDomain + "@@" + userEmail);
		}
	}
}
