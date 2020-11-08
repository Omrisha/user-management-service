package demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
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
		this.nameBoundary = new NameBoundary(MOCK_NAME_FIRST, MOCK_NAME_LAST);
		this.userBoundary = new UserBoundary(MOCK_USER_EMAIL,
				this.nameBoundary,
				MOCK_USER_PASSWORD,
				MOCK_USER_BIRTHDATE,
				MOCK_USER_ROLES);
	}
	
	@BeforeEach
	@AfterEach
	public void tearDown() {
		this.service.deleteAll();
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
	public void TestExistingUserStore() throws Exception {
		// TODO update storage service and implement test
	}
	
	@Test
	public void TestIllegalPasswordUserStore() throws Exception {
		// TODO implement test
	}
	
	@Test
	public void TestIllegalBirthdateUserStore() throws Exception {
		// TODO implement test
	}

}
