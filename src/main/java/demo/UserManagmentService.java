package demo;

public interface UserManagmentService {
    UserBoundary store(UserBoundary user);

    SecuredUserBoundary get(String email);

    SecuredUserBoundary login(String email, String password);

    void updateUser(String email, UserBoundary user);

    void deleteAll();

    SecuredUserBoundary[] search(String criteriaType, String value, String size, String page, String sortBy, String sortOrder);
}
