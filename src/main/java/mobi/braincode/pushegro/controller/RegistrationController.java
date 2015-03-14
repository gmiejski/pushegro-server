package mobi.braincode.pushegro.controller;

import mobi.braincode.pushegro.domain.User;
import mobi.braincode.pushegro.repository.UserRepository;
import mobi.braincode.pushegro.scheduler.ScheduledWatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static java.lang.String.format;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepository;
    private ScheduledWatcher scheduledWatcher;

    @Autowired
    public RegistrationController(UserRepository userRepository, ScheduledWatcher scheduledWatcher) {
        this.userRepository = userRepository;
        this.scheduledWatcher = scheduledWatcher;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public String registerUser(@RequestBody @Valid User user) {
        userRepository.registerUser(user);
        scheduledWatcher.registerUser(user);

        return format("user registered:%s\nGCMID:%s", user.getUsername(), user.getGcmId());
    }
}
