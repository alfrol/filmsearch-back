package ee.taltech.iti0203backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "wishlist")
@Data
@NoArgsConstructor
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "film_id")
    private Long filmId;

    public WishList(String username, Long filmId) {
        this.username = username;
        this.filmId = filmId;
    }
}
