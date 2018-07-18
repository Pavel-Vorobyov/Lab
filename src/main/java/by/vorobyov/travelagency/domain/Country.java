package by.vorobyov.travelagency.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@NamedQuery(name = "readAllCountriesQuery", query = "FROM Country")
@Table(name = "country")
public class Country implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private long id;

    @NotNull
    @Size(min = 5, max = 40)
    @Column(name = "name")
    private String name;

    @Builder
    public Country(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
