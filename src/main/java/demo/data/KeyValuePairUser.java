package demo.data;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.layout.UserBoundary;

public class KeyValuePairUser {
	private String key;
	private Map<String, Object> value;

	public KeyValuePairUser() {
	}

	public KeyValuePairUser(String key, Map<String, Object> value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public UserBoundary getValueAsUserBoundary() {
		return new ObjectMapper().convertValue(this.value, UserBoundary.class);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, Object> getValue() {
		return value;
	}

	public void setValue(Map<String, Object> value) {
		this.value = value;
	}

}