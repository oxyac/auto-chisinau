package oxyac.shopping.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import oxyac.shopping.data.entity.Car;
import oxyac.shopping.data.entity.Website;
import oxyac.shopping.data.repo.CarRepository;
import oxyac.shopping.data.repo.WebsiteRepository;
import oxyac.shopping.rest.dto.CarDto;
import oxyac.shopping.rest.dto.WebsiteDto;
import oxyac.shopping.rest.dto.abs.Converter;

import java.util.List;

@RestController
@CrossOrigin("*")
@Slf4j
public class CarsController {

    private final CarRepository carRepository;

    private final Converter carConverter;

    private final WebsiteRepository websiteRepository;

    public CarsController(CarRepository carRepository, Converter carConverter, WebsiteRepository websiteRepository) {
        this.carRepository = carRepository;
        this.carConverter = carConverter;
        this.websiteRepository = websiteRepository;
    }

    @GetMapping("/all")
    Page<CarDto> all(Pageable pageable) {
        Page<Car> carPage = carRepository.findAll(pageable);
        return carPage.map(car -> (CarDto) carConverter.createFrom(car));
    }

    @GetMapping("/site")
    Page<CarDto> site(@RequestParam Long websiteId, Pageable pageable) {
        log.info(String.valueOf(websiteId));
        Page<Car> carPage = carRepository.findByWebsite_Id(websiteId, pageable);
        return carPage.map(car -> (CarDto) carConverter.createFrom(car));
    }

    @GetMapping("/available")
    List<WebsiteDto> available() {
        List<Website> websites = (List<Website>) websiteRepository.findAll();
        return websites.stream().map(website -> (WebsiteDto) carConverter.createFrom(website)).toList();
    }

    @GetMapping("/test")
    String test() {
        return "TEST";
    }
}