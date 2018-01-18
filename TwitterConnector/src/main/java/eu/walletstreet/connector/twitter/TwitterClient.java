package eu.walletstreet.connector.twitter;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring wrapper that configures and manages the hbc twitter {@Link Client}.
 */
@Service
public class TwitterClient {

    private final Client client;

    @Autowired
    public TwitterClient(
            SingletonQueueWrapper queueWrapper,
            @Value("${app.name}") String appName,
            @Value("${twitter.consumerKey}") String consumerKey,
            @Value("${twitter.consumerSecret}") String consumerSecret,
            @Value("${twitter.token}") String token,
            @Value("${twitter.secret}") String secret,
            @Value("${app.terms}") String[] terms) {
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        Authentication hosebirdAuth = new OAuth1(consumerKey, consumerSecret, token, secret);

        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
        hosebirdEndpoint.trackTerms(Arrays.asList(terms));
//        hosebirdEndpoint.followings(Arrays.asList(followings));

        ClientBuilder clientBuilder = new ClientBuilder().name(appName).hosts(hosebirdHosts).authentication
                (hosebirdAuth).endpoint(hosebirdEndpoint);
        client = clientBuilder.processor(new StringDelimitedProcessor(queueWrapper.getQueue())).build();
    }

    public void readTweets() {
        client.connect();
    }

    @PreDestroy
    public void cleanUp() {
        client.stop();
    }

    public Map<String, Object> getMetrics(){
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("total number 200s", client.getStatsTracker().getNum200s());
        metrics.put("total number 400s", client.getStatsTracker().getNum400s());
        metrics.put("total number 500s", client.getStatsTracker().getNum500s());
        metrics.put("total number number connection failures", client.getStatsTracker().getNumConnectionFailures());
        metrics.put("total number messages", client.getStatsTracker().getNumMessages());
        metrics.put("total number disconnects", client.getStatsTracker().getNumDisconnects());
        metrics.put("done?",client.isDone());
        return metrics;
    }


}
