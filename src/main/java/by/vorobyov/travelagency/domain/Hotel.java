package by.vorobyov.travelagency.domain;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@NamedQuery(name = "readAllHotelsQuery", query = "FROM Hotel")
@Table(name = "hotel")
public class Hotel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private long id;

    @NotNull
    @Size(min = 5, max = 40)
    @Column(name = "name")
    private String name;

    @NotNull
    @Size(min = 5, max = 20)
    @Column(name = "phone")
    private String phone;

    @NotNull
    @ManyToMany
    @JoinColumn(name = "country_id")
    private Country country;

    @NotNull
    @Column(name = "stars")
    private int stars;

    @Builder
    public Hotel(long id, String name, String phone, Country country, int stars) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.country = country;
        this.stars = stars;
    }
}
