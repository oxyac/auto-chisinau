package oxyac.shopping.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Car extends AbstractEntity{

    @Column(unique=true)
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

}
