package demo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ReflectedUserBoudary extends UserBoundary {

	@Override
	@JsonIgnore
	public String getPassword() {
		return super.getPassword();
	}

}
