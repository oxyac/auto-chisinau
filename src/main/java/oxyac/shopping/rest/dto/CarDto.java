package oxyac.shopping.rest.dto;

import lombok.Data;

@Data
public class CarDto {
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
