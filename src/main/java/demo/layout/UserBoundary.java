package demo;

import javax.validation.constraints.Email;

import org.springframework.lang.NonNull;

public class UserBoundary {
	@NonNull
	@Email(message = "Email must be valid")
    private String email;
	@NonNull
    private NameBoundary name;
	@ValidPassword
    private String password;
	@NonNull
    private String birthdate;
	@NonNull
    private String[] roles;

    public UserBoundary() {}

    public UserBoundary(String email, NameBoundary name, String password, String birthdate, String[] roles) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.birthdate = birthdate;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NameBoundary getName() {
        return name;
    }

    public void setName(NameBoundary name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
