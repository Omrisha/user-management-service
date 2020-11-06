package demo;

public class SecuredUserBoundary {
    private String email;
    private NameBoundary name;
    private String birthdate;
    private String[] roles;

    public SecuredUserBoundary() {
    }

    public SecuredUserBoundary(String email, NameBoundary name, String birthdate, String[] roles) {
        this.email = email;
        this.name = name;
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
