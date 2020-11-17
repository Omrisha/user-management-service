package demo.layout;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ReflectedUserBoundary extends UserBoundary {
	
	public ReflectedUserBoundary() {}

	public ReflectedUserBoundary(UserBoundary user) {
		super(user.getEmail(),
				user.getName(),
				user.getPassword(),
				user.getBirthdate(),
				user.getRoles());
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return super.getPassword();
	}

}
