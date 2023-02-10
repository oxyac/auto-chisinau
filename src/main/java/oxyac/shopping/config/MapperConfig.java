package oxyac.shopping.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import oxyac.shopping.data.entity.Car;
import oxyac.shopping.rest.dto.CarDto;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Car, CarDto> propertyMapper = modelMapper.createTypeMap(Car.class, CarDto.class);
        propertyMapper.addMappings(
                mapper -> {
                    mapper.map(src -> src.getWebsite().getProtocol(), CarDto::setProtocol);
                    mapper.map(src -> src.getWebsite().getHost(), CarDto::setHost);
                    mapper.map(src -> src.getWebsite().getIconUri(), CarDto::setIconUri);
                }
        );
        return modelMapper;
    }
}
