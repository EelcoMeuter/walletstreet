package eu.walletstreet.connector.coinmarketcap;

import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Scope("singleton")
public class DataCollector {

    private final TotalMarketCapConnector connector;

    private LocalDateTime start = LocalDateTime.now().minusMonths(4).minusDays(1L);

    public DataCollector(TotalMarketCapConnector connector){
        this.connector = connector;
    }

    @Scheduled(fixedRate =3333 )
    public void collect(){
        start = start.plusDays(1l);
        if(start.isBefore(LocalDateTime.now())) {
            connector.getData(start);
        }
    }

}
