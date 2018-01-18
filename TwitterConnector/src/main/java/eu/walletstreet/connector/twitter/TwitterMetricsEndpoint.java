package eu.walletstreet.connector.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TwitterMetricsEndpoint implements Endpoint<Map<String, Object>> {

    private TwitterClient client;

    @Autowired
    public TwitterMetricsEndpoint(TwitterClient client) {
        this.client = client;
    }

    @Override
    public String getId() {
        return "twitterMetrics";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isSensitive() {
        return false;
    }

    @Override
    public Map<String, Object> invoke() {
        return client.getMetrics();
    }
}
