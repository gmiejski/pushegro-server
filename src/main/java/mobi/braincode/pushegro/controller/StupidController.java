package mobi.braincode.pushegro.controller;


import mobi.braincode.pushegro.domain.User;
import mobi.braincode.pushegro.gcm.GcmNotifier;
import mobi.braincode.pushegro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Set;

@RestController
@RequestMapping("/")
public class StupidController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GcmNotifier notifier;

    @RequestMapping(method = RequestMethod.GET)
    public String stupidString() {
        return "working";
    }

    @RequestMapping(value = "ping", method = RequestMethod.GET)
    public String ping() {
        Set<User> users = userRepository.getUsers();


        ArrayList<Long> predicatesChanged = new ArrayList<>();
        predicatesChanged.add(1L);

        users.stream().forEach(user -> notifier.notify(user, predicatesChanged));

        return "ping pong";
    }
}
