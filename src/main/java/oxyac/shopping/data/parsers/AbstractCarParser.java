package oxyac.shopping.data.parsers;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import oxyac.shopping.data.entity.Car;
import oxyac.shopping.data.entity.Website;
import oxyac.shopping.data.repo.CarRepository;
import oxyac.shopping.data.repo.WebsiteRepository;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public abstract class AbstractCarParser implements CarParser {

    protected final WebsiteRepository websiteRepository;
    private final CarRepository carRepository;
    protected Document document;
    protected URI host;
    protected Set<Car> cars = new HashSet<>();

    protected Website website = new Website();

    public AbstractCarParser(WebsiteRepository websiteRepository, CarRepository carRepository) {
        this.websiteRepository = websiteRepository;
        this.carRepository = carRepository;
    }

    public void parse() {
        log.info("Start seed on URI: {}", getUriToParse());
        if (getUriToParse().equals("")) {
            return;
        }
        try {
            document = Jsoup.connect(getUriToParse()).get();
            host = new URI(getUriToParse());
        } catch (Throwable e) {
            log.error("Failed to fetch URI: " + getUriToParse(), e);
            return;
        }
        parseWebsiteData();

        parseUri();
        persist();
        flushOldData();
        log.info("End Seed {}", website.getHost());
    }

    private void flushOldData() {
        Long lastId = carRepository.findByWebsiteOrderByForeignIdDesc(website).orElse(0L);
        carRepository.deleteByForeignIDLessThanAndWebsite(lastId, website);
    }

    void persist() {
        List<Long> existingForeignIds = carRepository.getForeignIdsByWebsite(website).orElse(List.of());
        cars.removeIf(s -> existingForeignIds.contains(s.getForeignId()));
        try {
            carRepository.saveAll(cars);
            cars.clear();
        } catch (Throwable e) {
            log.error("Failed to persist cars", e);
        }

    }

    @Override
    public void parseWebsiteData() {
        website = websiteRepository.findByUriToParse(getUriToParse());
        if(website.getId() != null){
            return;
        }
        website = new Website();
        Element e = document.head().select("link[href~=.*\\.ico]").first();
        String iconUri = e != null ? e.attr("href") : parseFavicon();
        try {
            website.setProtocol(host.toURL().getProtocol() + "://");
        } catch (MalformedURLException ex) {
            log.error(ex.toString());
        }
        iconUri = website.getProtocol() + host.getHost() + iconUri;
        log.info("{} :: Favicon location: {}", host.getHost(), iconUri);
        website.setHost(host.getHost());
        website.setUriToParse(getUriToParse());
        website.setIconUri(iconUri);
        websiteRepository.save(website);
    }

    void saveCar(Car car) {
        car.setWebsite(website);
        cars.add(car);
    }
}
