package demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.RestTemplate;

import demo.layout.NameBoundary;
import demo.layout.UserBoundary;
import demo.logic.KeyValueStoreBasedUserManagementService;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class KeyValueStoreBasedUsersManagementServiceTests {
	private static final String HTTP_LOCALHOST = "http://localhost:";
	private static final String MOCK_USER_EMAIL = "user@afeka.ac.il";
	private static final String MOCK_USER_PASSWORD = "ab4de";
	private static final String MOCK_USER_BIRTHDATE = "19-11-1963";
	private static final String[] MOCK_USER_ROLES = {"admin","devs","inspector"};
	private static final String MOCK_NAME_FIRST = "Cynthia";
	private static final String MOCK_NAME_LAST = "Chambers";
	private KeyValueStoreBasedUserManagementService service;
	private RestTemplate restTemplate;
	private String url;
	private int port;
	private UserBoundary userBoundary;
	private NameBoundary nameBoundary;
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}

	@Autowired
	public void setService(KeyValueStoreBasedUserManagementService service) {
		this.service = service;
	}
	
	@PostConstruct
	public void init() {
		this.url = HTTP_LOCALHOST + port;
		this.restTemplate = new RestTemplate();
	}
	
	@BeforeEach
	@AfterEach
	public void tearDown() {
		this.service.deleteAll();
		this.nameBoundary = new NameBoundary(MOCK_NAME_FIRST, MOCK_NAME_LAST);
		this.userBoundary = new UserBoundary(MOCK_USER_EMAIL,
				this.nameBoundary,
				MOCK_USER_PASSWORD,
				MOCK_USER_BIRTHDATE,
				MOCK_USER_ROLES);
	}
	
	@Test
	public void TestUserStore() throws Exception {
		// GIVEN
		
		// WHEN a POST request for /users is received
		UserBoundary actualResults = this.restTemplate.postForObject(
				this.url + "/users",
				this.userBoundary,
				UserBoundary.class);
		
		// THEN the storage service returns a similar user that was stored.
		assertThat(actualResults).isNotNull();
		assertThat(actualResults).hasFieldOrPropertyWithValue("email", MOCK_USER_EMAIL);
		assertThat(actualResults).hasFieldOrPropertyWithValue("password", MOCK_USER_PASSWORD);
		assertThat(actualResults).hasFieldOrPropertyWithValue("birthdate", MOCK_USER_BIRTHDATE);
		assertThat(actualResults).extracting("name").hasFieldOrPropertyWithValue("first", MOCK_NAME_FIRST);
		assertThat(actualResults).extracting("name").hasFieldOrPropertyWithValue("last", MOCK_NAME_LAST);
		assertThat(actualResults.getRoles()).containsAll(Arrays.asList(MOCK_USER_ROLES));
	}
	
	@Test
	public void TestExistingUserStoreThenExeptionIsThrown() throws Exception {
		BadRequest exception = assertThrows(BadRequest.class, () -> {
			// GIVEN an existing user
			service.store(userBoundary);
			// WHEN storing the same user
			this.restTemplate.postForObject(
					this.url + "/users",
					this.userBoundary,
					UserBoundary.class);
	    });
		// THEN an error with status code 400 is received
		String expectedMessage = "400";
	    String actualMessage = exception.getMessage();
	 
	    assertTrue(actualMessage.contains(expectedMessage));
		
	}
	
	@Test
	public void TestIllegalPasswordUserStore() throws Exception {
		// GIVEN a user with illegal password
		String illegal4CharsLongPassword = "aa1a";
		String illegalNoDigitPassword = "aaaaa";
		
		this.userBoundary.setPassword(illegal4CharsLongPassword);
		// WHEN storing them
		BadRequest exception1 = assertThrows(BadRequest.class, () -> {
			this.restTemplate.postForObject(
					this.url + "/users",
					this.userBoundary,
					UserBoundary.class);
	    });
		BadRequest exception2 = assertThrows(BadRequest.class, () -> {
			this.userBoundary.setPassword(illegalNoDigitPassword);
			this.restTemplate.postForObject(
					this.url + "/users",
					this.userBoundary,
					UserBoundary.class);
	    });
		// THEN an error with status code 400 is received
		String expectedMessage = "400";
	    String actualMessage = exception1.getMessage();
	 
	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    actualMessage = exception2.getMessage(); 
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void TestIllegalBirthdateUserStore() throws Exception {
		BadRequest exception1 = assertThrows(BadRequest.class, () -> {
			this.userBoundary.setBirthdate("19-11-196");
			this.restTemplate.postForObject(
					this.url + "/users",
					this.userBoundary,
					UserBoundary.class);
	    });
		BadRequest exception2 = assertThrows(BadRequest.class, () -> {
			this.userBoundary.setBirthdate("50-11-1963");
			this.restTemplate.postForObject(
					this.url + "/users",
					this.userBoundary,
					UserBoundary.class);
	    });
		BadRequest exception3 = assertThrows(BadRequest.class, () -> {
			this.userBoundary.setBirthdate("19-15-1963");
			this.restTemplate.postForObject(
					this.url + "/users",
					this.userBoundary,
					UserBoundary.class);
	    });
		BadRequest exception4 = assertThrows(BadRequest.class, () -> {
			this.userBoundary.setBirthdate("1-11-1963");
			this.restTemplate.postForObject(
					this.url + "/users",
					this.userBoundary,
					UserBoundary.class);
	    });
		BadRequest exception5 = assertThrows(BadRequest.class, () -> {
			this.userBoundary.setBirthdate("19-1-1963");
			this.restTemplate.postForObject(
					this.url + "/users",
					this.userBoundary,
					UserBoundary.class);
	    });
		
		String expectedMessage = "400";
		
	    String actualMessage = exception1.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    actualMessage = exception2.getMessage(); 
	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    actualMessage = exception3.getMessage(); 
	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    actualMessage = exception4.getMessage(); 
	    assertTrue(actualMessage.contains(expectedMessage));
	    
	    actualMessage = exception5.getMessage(); 
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void TestIllegalEmail() {
		BadRequest exception = assertThrows(BadRequest.class, () -> {
			this.userBoundary.setEmail("not an email");
			this.restTemplate.postForObject(
					this.url + "/users",
					this.userBoundary,
					UserBoundary.class);
	    });
		
		String expectedMessage = "400";
		
	    String actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void TestBlankName() {
		BadRequest exception = assertThrows(BadRequest.class, () -> {
			this.nameBoundary.setFirst("");
			this.nameBoundary.setLast("");
			this.userBoundary.setName(nameBoundary);
			this.restTemplate.postForObject(
					this.url + "/users",
					this.userBoundary,
					UserBoundary.class);
	    });
		
		String expectedMessage = "400";
		
	    String actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void TestBlankRole() {
		BadRequest exception = assertThrows(BadRequest.class, () -> {
			String[] roles = {"ok", ""};
			this.userBoundary.setRoles(roles);
			this.restTemplate.postForObject(
					this.url + "/users",
					this.userBoundary,
					UserBoundary.class);
	    });
		
		String expectedMessage = "400";
		
	    String actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void TestGetNotExistingUser() {
		NotFound exception = assertThrows(NotFound.class, () -> {
			this.restTemplate.getForObject(this.url + "/users/{key}",
					UserBoundary.class,
					this.userBoundary.getEmail());
	    });
		
		String expectedMessage = "404";
		
	    String actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void TestLogin() {
		service.store(userBoundary);
		UserBoundary actualResults = this.restTemplate.getForObject(this.url + "/users/login/{key}?password={password}",
				UserBoundary.class,
				this.userBoundary.getEmail(),
				this.userBoundary.getPassword());
		
		assertThat(actualResults).isNotNull();
		assertThat(actualResults).hasFieldOrPropertyWithValue("email", this.userBoundary.getEmail());
	}
	
	@Test
	void TestUpdateNotExistingUser() {
		NotFound exception = assertThrows(NotFound.class, () -> {
			this.restTemplate.put(this.url + "/users/{key}",
					this.userBoundary,
					this.userBoundary.getEmail()); 
	    });
		
		String expectedMessage = "404";
		
	    String actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	}

}
