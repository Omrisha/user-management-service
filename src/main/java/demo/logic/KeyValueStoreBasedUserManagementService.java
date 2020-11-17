package demo.logic;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import demo.errors.NotFoundException;
import demo.errors.UnauthorizedException;
import demo.layout.ReflectedUserBoundary;
import demo.layout.UserBoundary;
import demo.utils.Formatter;
import demo.data.KeyValuePairUser;
import demo.errors.BadRequestExeption;

@Service
public class KeyValueStoreBasedUserManagementService implements UserManagmentService {
	private RestTemplate restTemplate;
	private String url;
	private String protocol;
	private String host;
	private String path;
	private int port;
	
	@Value("${keyValueStoreService.protocol:http://}")
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	@Value("${keyValueStoreService.host:localhost:}")
	public void setHost(String host) {
		this.host = host;
	}
	
	@Value("${keyValueStoreService.path:/}")
	public void setPath(String path) {
		this.path = path;
	}
	
	@Value("${keyValueStoreService.port:80}")
	public void setPort(String port) {
		this.port = Integer.parseInt(port);
	}
	
	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();
		
		this.url = protocol + host + port + path;
	}

	@Override
	public UserBoundary store(UserBoundary user) {
		String key = user.getEmail();
		try {
			return this.restTemplate
					.postForObject(
						this.url + "/{key}", 
						user, 
						KeyValuePairUser.class, 
						key)
					.getValueAsUserBoundary();
		} catch (Exception e) {
			throw new BadRequestExeption();
		}
		
	}

	@Override
	public UserBoundary get(String email) {
		UserBoundary user = this.restTemplate
				.getForObject(
					this.url + "/{key}",
					ReflectedUserBoundary.class,
					email);
		// Check if user exists
		if (user == null)
			throw new NotFoundException("User not found.");
		
		return user;
	}

	@Override
	public UserBoundary login(String email, String password) {
		UserBoundary user = this.restTemplate
				.getForObject(
						this.url + "/{key}",
						UserBoundary.class,
						email);
		
		// Check if user exists
		if (user == null)
			throw new UnauthorizedException("Login attempt failed, user not found.");
		
		// Compare passwords
		if (user.comparePassword(password))
			return new ReflectedUserBoundary(user);
		
		throw new UnauthorizedException("Login attempt failed, wrong password.");
	}

	@Override
	public void updateUser(String email, UserBoundary user) {
		
		// Validate email parameter and email in body are the same
		if (!email.equals(user.getEmail()))
			throw new BadRequestExeption();
		
		// Check if user exists
		this.get(email);
		
		// Update user
		this.restTemplate
		.put(this.url + "/{key}",
				user,
				email);
	}

	@Override
	public void deleteAll() {
		this.restTemplate.delete(this.url);
	}

	@Override
	public UserBoundary[] search(String criteriaType, String value, String size, String page, String sortBy, String sortOrder) {
		
		// criteriaType provided without value
		if (!criteriaType.isEmpty() && value.isEmpty())
			throw new BadRequestExeption();
		
		switch (criteriaType) {
		case "":
			return this.kVStorageServiceSearch(
					size,
					page,
					sortBy,
					sortOrder);
			
		case "byLastName":
			return this.kVStorageServiceSearch(
					"name.last",
					value,
					"equals",
					size,
					page,
					sortBy,
					sortOrder);
			
		case "byMinimumAge":
			try {
				return this.kVStorageServiceSearch(
						"birthdate",
						Formatter.getFormatedCurrentDateMinusYears(Integer.parseInt(value)),
						"smallerThan",
						size,
						page,
						sortBy,
						sortOrder);
			} catch (NumberFormatException e) { // criteriaValue is not an integer
				throw new BadRequestExeption();
			}
			
			
		case "byRole":
			return this.kVStorageServiceSearch(
					"roles",
					value,
					"contains",
					size,
					page,
					sortBy,
					sortOrder);

		default:
			throw new NotFoundException("No such criteriaType.");
		}
	}
	
	private UserBoundary[] kVStorageServiceSearch(
			String criteriaType,
			String value,
			String operator,
			String size,
			String page,
			String sortBy,
			String sortOrder) {
		return this.restTemplate.getForObject(
				this.url + "/search?criteriaType={criteriaType}&criteriaValue={criteriaValue}&criteriaOperator={criteriaOperator}&size={size}&page={page}&sortBy={sortBy}&sortOrder={sortOrder}",
				ReflectedUserBoundary[].class,
				criteriaType, value, operator, size, page, sortBy, sortOrder);
	}
	
	private UserBoundary[] kVStorageServiceSearch(
			String size,
			String page,
			String sortBy,
			String sortOrder) {
		return this.restTemplate.getForObject(
				this.url + "/search?size={size}&page={page}&sortBy={sortBy}&sortOrder={sortOrder}",
				ReflectedUserBoundary[].class,
				size, page, sortBy, sortOrder);
	}

}
