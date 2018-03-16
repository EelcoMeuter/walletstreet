package eu.walletstreet.connector.coinmarketcap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static java.util.stream.Collectors.toList;

@Service
public class TotalMarketCapConnector {

    private RestTemplate template;

    private PriceEntryRepository repository;

    private final ScriptEngine jsEngine = new ScriptEngineManager().getEngineByName("nashorn");


    @Autowired
    public TotalMarketCapConnector(RestTemplate template, PriceEntryRepository repository) {
        this.template = template;
        this.repository = repository;
    }

    void getData(LocalDateTime dateTime) {
        String url = urlBuilder(dateTime);
        DataModel model = template.exchange(url, HttpMethod.GET, null, DataModel.class).getBody();
        System.out.println(String.format("%s: found %s rows", dateTime.format(DateTimeFormatter.ISO_DATE_TIME), model
                .getPriceUSD().size()));
        repository.save(model.getMarketCap().stream().map(d -> new MarketCap().map(d)).collect(toList()));
        repository.save(model.getPriceBTC().stream().map(d -> new PriceBTC().map(d)).collect(toList()));
        repository.save(model.getPriceUSD().stream().map(d -> new PriceUSD().map(d)).collect(toList()));
        repository.save(model.getVolumeUSD().stream().map(d -> new VolumeUSD().map(d)).collect(toList()));
    }

    private String urlBuilder(LocalDateTime timestamp) {
        return "https://graphs2.coinmarketcap.com/currencies/bitcoin/" + dateToLong(timestamp.minusDays(1L)) + "/" +
               dateToLong(timestamp) + "/";
    }

    long dateToLong(LocalDateTime timeStamp) {
        return timeStamp.withSecond(0).withNano(0).toInstant(ZoneOffset.UTC).toEpochMilli();
    }


}
