package demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserManagmentController {

    private UserManagmentService userManagementService;

    @RequestMapping(path = "/users",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserBoundary store(@RequestBody UserBoundary user)
    {
        return this.userManagementService.store(user);
    }

    @RequestMapping(path = "/users/{email}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserBoundary get(@PathVariable("email") String email)
    {
        return this.userManagementService.get(email);
    }

    @RequestMapping(path = "/users/login/{email}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserBoundary login(@PathVariable("email") String email,
                              @RequestParam(name="password", required = true)  String password)
    {
        return this.userManagementService.login(email, password);
    }
}
