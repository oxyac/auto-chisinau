package oxyac.shopping.rest.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import oxyac.shopping.data.entity.Car;
import oxyac.shopping.data.entity.Website;
import oxyac.shopping.rest.dto.CarDto;
import oxyac.shopping.rest.dto.WebsiteDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapperService {
    @Mapping(target = "protocol", source = "entity.website.protocol")
    @Mapping(target = "iconUri", source = "entity.website.iconUri")
    @Mapping(target = "host", source = "entity.website.host")
    CarDto carToCarDto(Car entity);

    List<WebsiteDto> websiteToWebsiteDto(List<Website> websites);
}
