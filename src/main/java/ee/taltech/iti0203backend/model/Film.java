package ee.taltech.iti0203backend.model;

import ee.taltech.iti0203backend.dto.FilmDto;
import ee.taltech.iti0203backend.utils.AgeRating;
import ee.taltech.iti0203backend.utils.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "film")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String producer;
    private String plot;
    private Short duration;

    @Column(name = "release_date")
    private Short releaseDate;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(name = "age_rating")
    @Enumerated(EnumType.STRING)
    private AgeRating ageRating;

    @Column(name = "image_path")
    private String imagePath;

    public Film(FilmDto filmDto) {
        this.title = filmDto.getTitle();
        this.producer = filmDto.getProducer();
        this.plot = filmDto.getPlot();
        this.duration = filmDto.getDuration();
        this.releaseDate = filmDto.getReleaseDate();
        this.genre = Genre.valueOf(filmDto.getGenre().toUpperCase());
        this.ageRating = AgeRating.fromString(filmDto.getAgeRating());
        this.imagePath = filmDto.getImagePath();
    }
}
