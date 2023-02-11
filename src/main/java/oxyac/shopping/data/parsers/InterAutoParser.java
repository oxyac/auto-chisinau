package oxyac.shopping.data.parsers;


import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import oxyac.shopping.data.entity.Car;
import oxyac.shopping.data.repo.CarRepository;
import oxyac.shopping.data.repo.WebsiteRepository;

import java.util.List;

@Slf4j
@Component
public class InterAutoParser extends AbstractCarParser {

    public InterAutoParser(WebsiteRepository websiteRepository, CarRepository carRepository) {
        super(websiteRepository, carRepository);

    }
    @Override
    public void parseUri() {
        Elements els = document.getElementsByClass("cd-offer-blk");
        for (Element el : els) {
            try {
                log.info(el.toString());
                Car car = new Car();
                Element img = el.getElementsByClass("cardojo-vehicle-block-thumbnail").get(0);
                Element name = el.getElementsByClass("cd-offer-blk__meta").get(0).getElementsByTag("a").get(0);
                Element mileage = el.getElementsByClass("car-spec").get(0);
                Element price = el.getElementsByClass("cardojo-Price-amount").get(0);
                Element currency = el.getElementsByClass("cardojo-Price-currencySymbol").get(0);
                String[] c = img.attr("style").split(":");
                String ima = String.join(":", List.of(c[1], c[2]));
                String s = ima.split("\\(")[1];
                s = s.split("\\)")[0];
                car.setImageUri(s);
                car.setLink(name.attr("href").replaceAll("https://interauto.md", ""));
                car.setCarName(name.text());
                String[] t = name.attr("href").split("/")[4].split("-");
                car.setForeignId(Long.valueOf(t[t.length - 1].substring(1)));
                car.setMileage(mileage.text().split(",")[0]);
                car.setPrice(price.text());
                if(car.getPrice().contains("Договор")){
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
        return "https://interauto.md/automobile/?search_keyword=&make=0&model=0&price_min=1499&price_max=10000&vehicle_year_min=1938&vehicle_year_max=2023&mileage_min=0&mileage_max=930734&vehicle_transmission=&vehicle_drive=&vehicle_exterior_color=&vehicle_cilinder_min=0&vehicle_cilinder_max=14681&vehicle_body_style=&vehicle_seats_min=2&vehicle_seats_max=52&vehicle_location=&tip_oferta=auto-de-vanzare&orderby=";
    }

    @Override
    public String parseFavicon() {
        return "/wp-content/uploads/2020/06/cropped-logo-icon-500x500.png";
    }

}
