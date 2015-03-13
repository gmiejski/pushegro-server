package mobi.braincode.pushegro.controller;

import mobi.braincode.pushegro.UserRepository;
import mobi.braincode.pushegro.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {


    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public String registerUser(@RequestBody User user) {
        try {
            userRepository.registerUser(user);
            return "user registered:" + user.getUsername() + "\n" +
                    "GCMID:" + user.getGcmId();
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }
}
