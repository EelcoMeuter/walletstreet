package eu.walletstreet.connector.coinmarketcap;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@MappedSuperclass
public abstract class AbstractPriceEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDateTime dateTime;

    private Double value;


    public Integer getId() {
        return id;
    }

    public Double getValue() {
        return value;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public AbstractPriceEntry map(List<Double> values){
        this.dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(values.get(0).longValue()), ZoneId.of("UTC"));
        this.value = values.get(1);
        return this;
    }
}
