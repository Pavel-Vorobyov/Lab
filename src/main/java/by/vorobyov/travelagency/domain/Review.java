package by.vorobyov.travelagency.domain;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@NamedQuery(name = "readAllReviewsQuery", query = "FROM Review")
@Table(name = "review")
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    private long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Max(1000)
    @Column(name = "content")
    private String content;

    @Builder
    public Review(long id, Tour tour, User user, String content) {
        this.id = id;
        this.tour = tour;
        this.user = user;
        this.content = content;
    }
}
