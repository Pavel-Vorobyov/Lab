package by.vorobyov.travelagency.domain;

import by.vorobyov.travelagency.utill.TourTypeConverter;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@NamedQuery(name = "readAllToursQuery", query = "FROM Tour")
@Table(name = "tour")
public class Tour implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Max(255)
    @Column(name = "photo")
    private String photo;

    @NotNull
    @FutureOrPresent
    @Column(name = "date")
    private LocalDate date;

    @NotNull
    @Column(name = "duration")
    private int duration;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id")
    private Country country;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @NotNull
    @Convert(converter = TourTypeConverter.class)
    @Column(name = "type_id")
    private TourType type;                               //TODO

    @NotNull
    @Max(1000)
    @Column(name = "description")
    private String description;

    @NotNull
    @PositiveOrZero
    @Column(name = "coast")
    private Double coast;

    @Builder
    public Tour(long id, String photo, LocalDate date, int duration, Country country, Hotel hotel
            , TourType type, String description, Double coast) {
        this.id = id;
        this.photo = photo;
        this.date = date;
        this.duration = duration;
        this.country = country;
        this.hotel = hotel;
        this.type = type;
        this.description = description;
        this.coast = coast;
    }
}
