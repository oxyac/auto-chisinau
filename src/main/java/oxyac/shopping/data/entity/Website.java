package oxyac.shopping.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Website extends AbstractEntity {
    @Column(length = 1000)
    String uriToParse;

    String host;

    String iconUri;

    String protocol;

    @OneToMany(mappedBy = "website", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Car> cars;
}
