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
public class MaklerParser extends AbstractCarParser {

    private static final String URI = "https://makler.md/ru/transport/cars?list&region[]=2&city[]=28&city[]=9&city[]=1494&city[]=11&city[]=1495&city[]=1034&city[]=1035&city[]=13&city[]=1033&city[]=1048&city[]=1049&city[]=1539&city[]=1042&city[]=17&city[]=1506&city[]=1043&city[]=1501&city[]=1502&city[]=1040&city[]=3466&city[]=1104&city[]=1105&city[]=23&city[]=1497&city[]=1496&city[]=1498&city[]=1037&city[]=1038&city[]=1106&city[]=31&city[]=32&city[]=33&city[]=1503&city[]=34&city[]=1039&city[]=1500&city[]=1538&city[]=37&city[]=1508&city[]=39&city[]=40&city[]=1509&city[]=1493&city[]=1510&city[]=1107&city[]=47&city[]=1511&city[]=1512&city[]=1108&city[]=1514&city[]=1044&city[]=1513&city[]=1045&city[]=1517&city[]=55&city[]=1518&city[]=1046&city[]=1047&city[]=1109&city[]=1504&city[]=1505&city[]=60&city[]=1110&city[]=2609&city[]=1499&city[]=1036&city[]=1515&city[]=1516&city[]=1507&city[]=67&kind[]=sell&field_316_min=2000&price_max=10000&currency_id=5&order=date&direction=desc&list=detail";

    public MaklerParser(WebsiteRepository websiteRepository, CarRepository carRepository) {
        super(websiteRepository, carRepository);
    }

    private static boolean shouldSkipElement(Element el) {
        return el.getElementsByClass("ls-detail_imgBlock").isEmpty()
                || el.getElementsByClass("ls-detail_antTitle").isEmpty()
                || el.getElementsByClass("ls-detail_price").isEmpty();
    }

    @Override
    public void parseUri() {
        Elements els = document.select("article");
        for (Element el : els) {
            if (shouldSkipElement(el)) continue;
            Car car = new Car();
            Element img = el.getElementsByClass("ls-detail_imgBlock").get(0).getElementsByTag("img").get(0);
            Element name = el.getElementsByClass("ls-detail_antTitle").get(0).getElementsByTag("a").get(0);
            Element price = el.getElementsByClass("ls-detail_price").get(0);
            car.setImageUri(img.attr("src"));
            car.setLink(name.attr("href"));
            car.setCarName(name.text());
            List<String> foreignId = List.of(name.attr("href").split("/"));
            car.setForeignId(Long.valueOf(foreignId.get(foreignId.size() - 1)));
            car.setMileage(null);
            car.setPrice(price.text());
            saveCar(car);
        }
    }

    @Override
    public String getUriToParse() {
        return URI;
    }

    @Override
    public String parseFavicon() {
        log.info("{} :: Custom favicon parsing", host);
        return "";
    }

}
