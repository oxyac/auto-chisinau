package oxyac.shopping.rest.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import oxyac.shopping.rest.dto.abs.AbstractDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class WebsiteDto extends AbstractDto {

    private String host;
    private Long id;

}
