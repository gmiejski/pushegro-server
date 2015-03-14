package mobi.braincode.pushegro.controller;

import mobi.braincode.pushegro.domain.User;
import mobi.braincode.pushegro.repository.InMemoryUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.format;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private InMemoryUserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public String registerUser(@RequestBody User user) {
        try {
            userRepository.registerUser(user);
            return format("user registered:%s\nGCMID:%s", user.getUsername(), user.getGcmId());
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }
}
