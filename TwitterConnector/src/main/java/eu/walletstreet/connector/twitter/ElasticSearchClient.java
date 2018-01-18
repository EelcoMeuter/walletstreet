package eu.walletstreet.connector.twitter;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Component
@Scope("singleton")
public class ElasticSearchClient {

    private static Logger logger = LoggerFactory.getLogger(ElasticSearchClient.class);

    private RestHighLevelClient client;

    @PostConstruct
    public void init() {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    public RestHighLevelClient getClient() {
        return client;
    }

    @PreDestroy
    public void destroy() {
        try {
            client.close();
        } catch (IOException e) {
            logger.error("Failed to close client!");
            logger.debug(e.getMessage(), e);
        }
    }
}
