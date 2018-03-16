package eu.walletstreet.connector.coinmarketcap;

import org.springframework.data.repository.CrudRepository;

public interface PriceEntryRepository<T extends AbstractPriceEntry> extends CrudRepository<T, Long> {

}
