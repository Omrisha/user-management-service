package demo;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserManagmentController {

    private UserManagmentService userManagementService;
    
    @Autowired
    public UserManagmentController(UserManagmentService userManagementService) {
		this.userManagementService = userManagementService;
	}

	@RequestMapping(path = "/users",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserBoundary store(@Valid @RequestBody UserBoundary user, BindingResult result)
    {
		if (result.hasErrors()) {
	        System.err.println("Validation Failed");
	        return null;
	    }
		
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
    
    @RequestMapping(path = "/users",
    		method = RequestMethod.DELETE)
    public void deleteAll() {
    	this.userManagementService.deleteAll();
    }
}
