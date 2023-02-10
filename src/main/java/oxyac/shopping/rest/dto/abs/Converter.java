package oxyac.shopping.rest.dto.abs;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import oxyac.shopping.data.entity.AbstractEntity;
import oxyac.shopping.data.entity.Car;
import oxyac.shopping.data.entity.Website;
import oxyac.shopping.rest.dto.WebsiteDto;
import oxyac.shopping.rest.dto.CarDto;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class Converter {

    private final ModelMapper modelMapper;

    protected Converter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AbstractDto createFrom(AbstractEntity entity) {
        AbstractDto abstractDto1 = null;
        try {
            abstractDto1 = findDto(entity).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage());
        }
        modelMapper.map(entity, abstractDto1);
        return abstractDto1;
    }

    protected Class<? extends AbstractDto> findDto(AbstractEntity entity) {

        Map<Class<? extends AbstractEntity>, Class<? extends AbstractDto>> classEntitytoMap = new HashMap<>();
        classEntitytoMap.put(Car.class, CarDto.class);
        classEntitytoMap.put(Website.class, WebsiteDto.class);

        return classEntitytoMap.get(entity.getClass());

    }
}
