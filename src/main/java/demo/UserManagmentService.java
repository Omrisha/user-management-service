package demo;

public interface UserManagmentService {
    UserBoundary store(UserBoundary user);

    UserBoundary get(String email);

    UserBoundary login(String email, String password);

    void updateUser(String email, UserBoundary user);

    void deleteAll();

    UserBoundary[] search(String criteriaType, String value, String size, String page, String sortBy, String sortOrder);
}
