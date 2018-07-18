package by.vorobyov.travelagency.domain;


import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@NamedQuery(name = "readAllUsersQuery", query = "FROM User")
@Table(name = "\"user\"")
public class User implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Size(min = 5, max = 10)
    @Column(name = "login")
    private String login;

    @NotNull
    @Size(min = 5, max = 10)
    @Column(name = "password")
    private String password;

    @NotNull
    @Size(min = 5, max = 20)
    @Column(name = "name")
    private String name;

    @NotNull
    @Size(min = 5, max = 20)
    @Column(name = "surname")
    private String surname;

    @Builder
    public User(long id, String login, String password, String name, String surname) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = name;
    }
}
