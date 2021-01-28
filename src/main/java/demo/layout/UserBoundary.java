package demo.layout;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.lang.NonNull;

import demo.validators.NotEmptyElements;
import demo.validators.ValidPassword;

public class
UserBoundary {
	@NotBlank
	@Email(message = "Email must be valid")
    private String email;
	@Valid
    private NameBoundary name;
	@ValidPassword
    private String password;
	@Pattern(regexp = "^[0-3]{1}[0-9]{1}-[0-1]{1}[0-9]{1}-[1-9]{1}[0-9]{3}$")
    private String birthdate;
	@NonNull
	@NotEmptyElements
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
    
    public boolean comparePassword(String password) {
    	return this.password.equals(password);
    }
}
