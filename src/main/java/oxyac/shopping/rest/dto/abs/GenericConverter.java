package oxyac.shopping.rest.dto.abs;

import oxyac.shopping.data.entity.AbstractEntity;

public interface GenericConverter<D extends AbstractDto, E extends AbstractEntity> {

    D createFrom(E entity);

}