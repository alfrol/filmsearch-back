package ee.taltech.iti0203backend.dto;

import ee.taltech.iti0203backend.model.Film;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * This class is used for displaying the film information on the main page.
 *
 * The class doesn't contain all of the {@link Film} class properties but
 * instead it contains only the ones that are needed when showing the film
 * to the user when they are on the main page of the application.
 *
 * Which properties to display can be a topic of discussion but we assume
 * that the client wants to see:
 *
 * 1. The title of the film
 * 2. The release date
 * 3. The genre
 * 4. The image
 *
 * (The {@code id} property is not meant for the client but rather to make
 * the access to the film from the database easier while processing, for example
 * GET requests)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainPageFilmDto {

    private Long id;
    private String title;
    private Short releaseDate;
    private String genre;
    private String imagePath;

    public MainPageFilmDto(Film film) {
        this.id = film.getId();
        this.title = film.getTitle();
        this.releaseDate = film.getReleaseDate();
        this.genre = film.getGenre().getValue();
        this.imagePath = film.getImagePath();
    }
}
