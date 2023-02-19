package oxyac.shopping.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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
    @ToString.Exclude
    private Set<Car> cars;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Website website = (Website) o;
        return getId() != null && Objects.equals(getId(), website.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
