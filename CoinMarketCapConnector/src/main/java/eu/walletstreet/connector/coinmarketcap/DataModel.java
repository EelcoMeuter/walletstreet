package eu.walletstreet.connector.coinmarketcap;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DataModel {

    @JsonProperty("market_cap_by_available_supply")
    private List<List<Double>> marketCap;

    @JsonProperty("price_btc")
    private List<List<Double>> priceBTC;

    @JsonProperty("price_usd")
    private List<List<Double>> priceUSD;

    @JsonProperty("volume_usd")
    private List<List<Double>> volumeUSD;

    public List<List<Double>> getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(List<List<Double>> marketCap) {
        this.marketCap = marketCap;
    }

    public List<List<Double>> getPriceBTC() {
        return priceBTC;
    }

    public void setPriceBTC(List<List<Double>> priceBTC) {
        this.priceBTC = priceBTC;
    }

    public List<List<Double>> getPriceUSD() {
        return priceUSD;
    }

    public void setPriceUSD(List<List<Double>> priceUSD) {
        this.priceUSD = priceUSD;
    }

    public List<List<Double>> getVolumeUSD() {
        return volumeUSD;
    }

    public void setVolumeUSD(List<List<Double>> volumeUSD) {
        this.volumeUSD = volumeUSD;
    }
}
