package demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
		return this.restTemplate
			.postForObject(
				this.url + "/{key}", 
				user, 
				KeyValuePairUser.class, 
				key)
			.getValueAsUserBoundary();
	}

	@Override
	public UserBoundary get(String email) {
		return this.restTemplate
				.getForObject(
					this.url + "/{key}",
					ReflectedUserBoudary.class,
					email);
	}

	@Override
	public UserBoundary login(String email, String password) {
		ReflectedUserBoudary kvpUser = this.restTemplate
				.getForObject(this.url + "/{key}",
						ReflectedUserBoudary.class,
						email);

		return kvpUser.getPassword() == password ? kvpUser : new ReflectedUserBoudary();
	}

	@Override
	public void updateUser(String email, UserBoundary user) {
		ReflectedUserBoudary kvpUser = this.restTemplate
				.getForObject(this.url + "/{key}",
						ReflectedUserBoudary.class,
						email);

		if (user.getPassword() != null)
			kvpUser.setPassword(user.getPassword());
		if (user.getBirthdate() != null)
			kvpUser.setBirthdate(user.getBirthdate());
		if (user.getRoles() != null)
			kvpUser.setRoles(user.getRoles());
		if (user.getName() != null) {
			if (user.getName().getFirst() != null && user.getName().getLast() != null)
				kvpUser.setName(new NameBoundary(user.getName().getFirst(), user.getName().getLast()));
		}

		this.restTemplate.put(this.url + "/{key}",
				kvpUser,
				KeyValuePairUser.class,
				email);
	}

	@Override
	public void deleteAll() {
		this.restTemplate.delete(this.url);
	}

	@Override
	public UserBoundary[] search(String criteriaType, String value, String size, String page, String sortBy, String sortOrder) {
		return new UserBoundary[0];
	}

}
