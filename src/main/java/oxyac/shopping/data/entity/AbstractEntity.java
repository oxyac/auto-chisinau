package oxyac.shopping.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@MappedSuperclass
@Access(AccessType.FIELD)
@Data
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Date timestamp;
}
