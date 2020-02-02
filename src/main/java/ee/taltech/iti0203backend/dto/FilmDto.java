package ee.taltech.iti0203backend.dto;

import ee.taltech.iti0203backend.model.Film;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDto {

    private Long id;
    private String title;
    private String producer;
    private String plot;
    private Short duration;
    private Short releaseDate;
    private String genre;
    private String ageRating;
    private String imagePath;

    public FilmDto(Film film) {
        this.id = film.getId();
        this.title = film.getTitle();
        this.producer = film.getProducer();
        this.plot = film.getPlot();
        this.duration = film.getDuration();
        this.releaseDate = film.getReleaseDate();
        this.genre = film.getGenre().getValue();
        this.ageRating = film.getAgeRating().getRating();
        this.imagePath = film.getImagePath();
    }
}
