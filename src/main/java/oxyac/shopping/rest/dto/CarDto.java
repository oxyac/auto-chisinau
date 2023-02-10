package oxyac.shopping.rest.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import oxyac.shopping.rest.dto.abs.AbstractDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class CarDto extends AbstractDto {

    private Long id;
    private Long foreignId;
    private String imageUri;
    private String carName;
    private String price;
    private String mileage;
    private String link;

    private String host;

    private String iconUri;

    private String protocol;

}
