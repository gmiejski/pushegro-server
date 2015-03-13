package mobi.braincode.pushegro.gcm;

import mobi.braincode.pushegro.domain.User;
import mobi.braincode.pushegro.gcm.api.Message;
import mobi.braincode.pushegro.gcm.api.Sender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class GcmNotifier {

    public static final String API_CONSOLE_KEY = "AIzaSyCzqbXwPQm-A1xskxgHZc93z5GcN3rgDqE";
    public static final String SERVER_MESSAGE = "predicatesChanged";

    public void notify(User user, List<String> predicatesChanged) {
        String joinedPredicatesIds = String.join(",", predicatesChanged);
        Message build = new Message.Builder().addData(SERVER_MESSAGE, joinedPredicatesIds)
                .timeToLive(10)
                .build();
        try {
            new Sender(API_CONSOLE_KEY).send(build, user.getGcmId().getId(), 5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
