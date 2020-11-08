package demo;

public interface UserManagmentService {
    
	public UserBoundary store(UserBoundary user);

	public UserBoundary get(String email);

	public UserBoundary login(String email, String password);

	public void deleteAll();
}
