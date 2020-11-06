package demo;

public interface UserManagmentService {
    UserBoundary store(UserBoundary user);

    UserBoundary get(String email);

    UserBoundary login(String email, String password);
}
