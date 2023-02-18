package oxyac.shopping.data.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Person extends AbstractEntity{

    @Column(unique=true)
    @EqualsAndHashCode.Include
    private String username;
    @Column(unique=true)
    @EqualsAndHashCode.Include
    private String email;
    private String password;

}
