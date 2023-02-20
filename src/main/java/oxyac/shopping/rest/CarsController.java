package oxyac.shopping.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import oxyac.shopping.data.entity.Car;
import oxyac.shopping.data.entity.Website;
import oxyac.shopping.data.repo.CarRepository;
import oxyac.shopping.data.repo.WebsiteRepository;
import oxyac.shopping.rest.dto.CarDto;
import oxyac.shopping.rest.dto.WebsiteDto;
import oxyac.shopping.rest.mapper.MapperService;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@Slf4j
public class CarsController {

    private final CarRepository carRepository;

    private final MapperService mapperService;

    private final WebsiteRepository websiteRepository;

    public CarsController(CarRepository carRepository, MapperService mapperService, WebsiteRepository websiteRepository) {
        this.carRepository = carRepository;
        this.mapperService = mapperService;
        this.websiteRepository = websiteRepository;
    }

    @GetMapping("/all")
    Page<CarDto> all(Pageable pageable) {
        Page<Car> carPage = carRepository.findAll(pageable);
        return carPage.map(mapperService::carToCarDto);
    }

    @GetMapping("/site")
    Page<CarDto> site(@RequestParam Long websiteId, Pageable pageable) {
        Page<Car> carPage = carRepository.findByWebsite_Id(websiteId, pageable);
        return carPage.map(mapperService::carToCarDto);
    }

    @GetMapping("/available")
    List<WebsiteDto> available() {
        List<Website> websites = (List<Website>) websiteRepository.findAll();
        return mapperService.websiteToWebsiteDto(websites);
    }

    @GetMapping("/test")
    String test() {
        return "TEST";
    }
}
