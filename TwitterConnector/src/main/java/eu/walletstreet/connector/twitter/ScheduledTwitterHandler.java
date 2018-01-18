package eu.walletstreet.connector.twitter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Simple tweet handler that reads the tweets at a fixed interval and pushes them to elasticsearch. The principle
 * should be self-balancing as the {@link Scheduled} annotation ensures the execution on a separate thread. This
 * implies that on heavy load many instance of this class will process the tweets.
 */
@Component
public class ScheduledTwitterHandler {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTwitterHandler.class);

    private final BlockingQueue<String> queue;

    private final ObjectMapper objectMapper;

    private final RestHighLevelClient client;

    @Autowired
    public ScheduledTwitterHandler(SingletonQueueWrapper queueWrapper, ObjectMapper objectMapper,
                                   ElasticSearchClient client) {
        this.queue = queueWrapper.getQueue();
        this.objectMapper = objectMapper;
        this.client = client.getClient();
    }

    @Scheduled(fixedRate = 500)
    public void handle() throws InterruptedException {
        while (!queue.isEmpty()) {
            String tweet = queue.take();
            try {
                JsonNode root = objectMapper.readValue(tweet, JsonNode.class);
                JsonNode lang = root.get("lang");
                JsonNode id = root.get("id");
                JsonNode text = root.get("text");
                //return only the english tweets with an id and text
                if (lang != null && "en".equals(lang.asText()) && id != null && text != null) {
                    XContentBuilder source = jsonBuilder().startObject()
                                                          .field("twitter_id", id.asLong())
                                                          .field("tweet",
                                                                 tweet)
                                                          .field("postDate", new Date())
                                                          .endObject();
                    IndexResponse response = client.index(new IndexRequest("twitter", "tweet").source(source));
                 }
            } catch (IOException e) {
                logger.error("Failed to write to elasticsearch cluster!");
                logger.debug(e.getMessage(), e);
            }
        }
    }

}
