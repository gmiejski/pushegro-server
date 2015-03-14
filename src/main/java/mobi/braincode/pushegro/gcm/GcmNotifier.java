package mobi.braincode.pushegro.gcm;

import mobi.braincode.pushegro.domain.User;
import mobi.braincode.pushegro.domain.Watcher;
import mobi.braincode.pushegro.domain.predicate.AuctionPredicate;
import mobi.braincode.pushegro.gcm.api.Message;
import mobi.braincode.pushegro.gcm.api.Sender;
import mobi.braincode.pushegro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class GcmNotifier {

    public static final String API_CONSOLE_KEY = "AIzaSyCzqbXwPQm-A1xskxgHZc93z5GcN3rgDqE";
    public static final String SERVER_MESSAGE = "predicatesChanged";
    public static final String SERVER_PREDICATES_NAMES = "predicatesNamesChanged";

    @Autowired
    private UserRepository userRepository;

    public void notify(User user, List<Long> predicatesChanged) {
        List<String> predicatesIdsInString = predicatesChanged.stream().map(String::valueOf).collect(toList());

        User completeUser = userRepository.loadUserByUsername(user.getUsername());
        List<String> auctionNames = completeUser.getWatchers().stream()
                .map(Watcher::getPredicate)
                .filter(x -> predicatesChanged.contains(x.getPredicateId()))
                .map(AuctionPredicate::getKeyword)
                .collect(toList());

        String joinedPredicatesIds = String.join(",", predicatesIdsInString);
        String joinedAuctionNames = String.join(",", auctionNames);

        Message build = new Message.Builder().addData(SERVER_MESSAGE, joinedPredicatesIds)
                .addData(SERVER_PREDICATES_NAMES, joinedAuctionNames)
                .timeToLive(10)
                .build();
        try {
            new Sender(API_CONSOLE_KEY).send(build, user.getGcmId().getId(), 5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
