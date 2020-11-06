package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserManagementServiceImpl implements UserManagmentService {
    private RestTemplate restTemplate;

    public UserManagementServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public UserBoundary store(UserBoundary user) {
        return null;
    }

    @Override
    public SecuredUserBoundary get(String email) {
        return null;
    }

    @Override
    public SecuredUserBoundary login(String email, String password) {
        return null;
    }

    @Override
    public void updateUser(String email, UserBoundary user) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public SecuredUserBoundary[] search(String criteriaType, String value, String size, String page, String sortBy, String sortOrder) {
        return new SecuredUserBoundary[0];
    }
}
