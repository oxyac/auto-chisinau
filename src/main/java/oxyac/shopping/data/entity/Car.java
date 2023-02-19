package oxyac.shopping.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Car extends AbstractEntity {

    @Column(unique = true)
    @EqualsAndHashCode.Include
    private Long foreignId;
    @Column(length = 1000)
    @ToString.Exclude
    private String imageUri;
    private String carName;
    private String price;
    private String mileage;
    private String link;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "website_id", nullable = false)
    private Website website;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(foreignId, car.foreignId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foreignId);
    }
}
