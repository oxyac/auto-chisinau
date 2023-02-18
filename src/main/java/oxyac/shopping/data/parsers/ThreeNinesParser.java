package oxyac.shopping.data.parsers;


import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import oxyac.shopping.data.entity.Car;
import oxyac.shopping.data.repo.CarRepository;
import oxyac.shopping.data.repo.WebsiteRepository;

@Slf4j
@Component
public class ThreeNinesParser extends AbstractCarParser {

    public ThreeNinesParser(WebsiteRepository websiteRepository, CarRepository carRepository) {
        super(websiteRepository, carRepository);

    }

    @Override
    public void parseUri() {
        Elements els = document.getElementsByClass("ads-list-photo");
        Element element = els.get(1);
        Elements els2 = element.getElementsByClass("ads-list-photo-item");
        for (Element el : els2) {
            try {
                Car car = new Car();
                Element img = el.getElementsByTag("img").get(0);
                Element name = el.getElementsByClass("ads-list-photo-item-title").get(0).getElementsByTag("a").get(0);
                Element mileage = el.getElementsByClass("is-offer-type").get(0).getElementsByTag("span").get(0);
                Element price = el.getElementsByClass("ads-list-photo-item-price-wrapper").get(0);
                car.setImageUri(img.attr("src"));
                car.setLink(name.attr("href"));
                car.setCarName(name.text());
                car.setForeignId(Long.valueOf(name.attr("href").split("/")[2]));
                car.setMileage(mileage.text());
                car.setPrice(price.text());
                if (car.getPrice().contains("Договор")) {
                    car.setPrice("$1");
                }
                saveCar(car);
            } catch (IndexOutOfBoundsException e) {
                log.error(el.toString());
            }
        }
    }

    @Override
    public String getUriToParse() {
        return "https://999.md/ru/list/transport/cars?hide_duplicates=no&applied=1&show_all_checked_childrens=no&ef=260,6,5,4112,2029,1279,2,4274,2339,2303,1077&r_6_2_from=&r_6_2_to=10000&r_6_2_unit=eur&r_6_2_negotiable=yes&o_4112_795=18895&r_2_103_from=2000&r_2_103_to=&r_2_103_unit=cm3&r_4274_107_from=&r_4274_107_to=";
    }

    @Override
    public String parseFavicon() {
        log.info("{} :: Custom favicon parsing", host);
        return "";
    }

}
