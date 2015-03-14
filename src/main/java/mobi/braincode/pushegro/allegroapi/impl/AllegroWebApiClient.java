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

import javax.xml.rpc.holders.IntHolder;
import javax.xml.rpc.holders.LongHolder;
import javax.xml.rpc.holders.StringHolder;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class AllegroWebApiClient {

    private static final int COUNTRY_CODE = 1; // Poland
    private AllegroWebApiPortType apiPort;
    private StringHolder sessionHolder;

    public AllegroWebApiClient(String username, String password, String webApiKey) {
        login(username, password, webApiKey);
    }

    private void login(String username, String encryptedPassword, String webApiKey) {
        AllegroWebApiServiceLocator service = new AllegroWebApiServiceLocator();

        try {

            apiPort = service.getAllegroWebApiPort();

            long localVerKey = readAllegroKey();

            StringHolder info = new StringHolder();
            LongHolder currentVerKey = new LongHolder();

            System.out.print("Receving webApiKey version... ");
            apiPort.doQuerySysStatus(1, COUNTRY_CODE, webApiKey, info, currentVerKey);
            System.out.println("done. Current version webApiKey=" + currentVerKey.value);

            if (localVerKey != currentVerKey.value) {
                System.out.println("Warning: webApiKey versions don't match!");
                localVerKey = currentVerKey.value;
            }

            sessionHolder = new StringHolder();
            LongHolder userId = new LongHolder();
            LongHolder serverTime = new LongHolder();
            System.out.print("Logging in... ");

            apiPort.doLoginEnc(username, encryptedPassword,
                    COUNTRY_CODE, webApiKey, localVerKey, sessionHolder, userId,
                    serverTime);
            System.out.println("done.");

        } catch (Exception e) {
            System.err.println(format("Error %s", e.getCause()));
        }
    }

    private String encryptAndEncodePassword(String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes("UTF-8"));
        return Base64.encode(md.digest());
    }

    private long readAllegroKey() {
        return 1426037600;
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
            e.printStackTrace();
            return Collections.emptySet();
        }
    }
}
