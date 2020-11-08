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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll() {
		this.restTemplate.delete(this.url);
	}

}
