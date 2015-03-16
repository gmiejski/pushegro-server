package mobi.braincode.pushegro.allegroapi.impl;

import mobi.braincode.pushegro.domain.auction.Auction;
import mobi.braincode.pushegro.domain.predicate.AuctionPredicate;
import mobi.braincode.pushegro.generated.AllegroWebApiPortType;
import mobi.braincode.pushegro.generated.AllegroWebApiServiceLocator;
import mobi.braincode.pushegro.generated.SearchOptType;
import mobi.braincode.pushegro.generated.holders.ArrayOfCategoriesStructHolder;
import mobi.braincode.pushegro.generated.holders.ArrayOfExcludedWordsHolder;
import mobi.braincode.pushegro.generated.holders.ArrayOfSearchResponseHolder;
import org.apache.axis.encoding.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.rpc.holders.IntHolder;
import javax.xml.rpc.holders.LongHolder;
import javax.xml.rpc.holders.StringHolder;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class AllegroWebApiClient {
    private final static Logger log = LoggerFactory.getLogger(AllegroWebApiClient.class);

    private static final int COUNTRY_CODE = 1; // Poland
    private static final String ALLEGRO_USERNAME = System.getenv("ALLEGRO_USERNAME");
    private static final String ALLEGRO_PASSWORD_BASE64 = System.getenv("ALLEGRO_PASSWORD_BASE64");
    private static final String ALLEGRO_KEY = System.getenv("ALLEGRO_KEY");

    private AllegroWebApiPortType apiPort;
    private StringHolder sessionHolder;

    public AllegroWebApiClient() {
        try {
            login(ALLEGRO_USERNAME, ALLEGRO_PASSWORD_BASE64, ALLEGRO_KEY);
        } catch (Exception e) {
            log.error("Error during login to allegro!: ", e);
        }
    }

    private void login(String username, String encryptedPassword, String webApiKey) {
        AllegroWebApiServiceLocator service = new AllegroWebApiServiceLocator();

        try {
            apiPort = service.getAllegroWebApiPort();


            StringHolder info = new StringHolder();
            LongHolder currentVerKey = new LongHolder();

            log.info("Receiving webApiKey version...");
            apiPort.doQuerySysStatus(1, COUNTRY_CODE, webApiKey, info, currentVerKey);
            log.info("done. Current version webApiKey={}", currentVerKey.value);

            sessionHolder = new StringHolder();
            LongHolder userId = new LongHolder();
            LongHolder serverTime = new LongHolder();
            log.info("Logging in... ");

            apiPort.doLoginEnc(username, encryptedPassword,
                    COUNTRY_CODE, webApiKey, currentVerKey.value, sessionHolder, userId,
                    serverTime);
            log.info("done.");

        } catch (Exception e) {
            log.error("Error: {}", e);
        }
    }

    private static String encryptAndEncodePassword(String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes("UTF-8"));
        return Base64.encode(md.digest());
    }

    public Set<Auction> loadAuctionsByPredicate(AuctionPredicate predicate) {

        IntHolder searchCount = new IntHolder();
        IntHolder searchCountFeatured = new IntHolder();

        try {
            ArrayOfSearchResponseHolder searchArray = new ArrayOfSearchResponseHolder();
            ArrayOfExcludedWordsHolder searchExcludedWords = new ArrayOfExcludedWordsHolder();
            ArrayOfCategoriesStructHolder searchCategories = new ArrayOfCategoriesStructHolder();

            SearchOptType searchQuery = new SearchOptType(predicate.getKeyword(), 0,
                    0, 0, 0, 0, 0, "", 0, 0, 0, 100, 0, 0);

            apiPort.doSearch(sessionHolder.value, searchQuery, searchCount,
                    searchCountFeatured, searchArray, searchExcludedWords,
                    searchCategories);

            return Arrays.stream(searchArray.value)
                    .map(wsdlAuction -> {
                        Auction auction = new Auction(wsdlAuction.getSItId(), wsdlAuction.getSItName());
                        auction.setEndDateTime(wsdlAuction.getSItEndingTime());
                        auction.setPrice(wsdlAuction.getSItPrice());
                        return auction;
                    })
                    .collect(toSet());

        } catch (Exception e) {
            log.error("Error occurred: Cause:", e);
            return Collections.emptySet();
        }
    }
}
