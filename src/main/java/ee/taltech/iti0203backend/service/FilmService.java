package ee.taltech.iti0203backend.service;

import ee.taltech.iti0203backend.dao.FilmRepository;
import ee.taltech.iti0203backend.dto.FilmDto;
import ee.taltech.iti0203backend.exception.FilmNotFoundException;
import ee.taltech.iti0203backend.exception.FilmValidationException;
import ee.taltech.iti0203backend.model.Film;
import ee.taltech.iti0203backend.utils.AgeRating;
import ee.taltech.iti0203backend.utils.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;

    /**
     * Get all films from the database.
     *
     * @return List of all films.
     */
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    /**
     * Find all films which contain the specified title form the database.
     *
     * @param title Title of the film to search for.
     * @return List of all possible films.
     */
    public List<Film> getFilmsByTitle(String title) {
        return filmRepository.findAllByTitle(title);
    }

    /**
     * Get film by its id.
     *
     * @param id The id of the specific film (unique).
     * @return Film with provided id.
     * @throws FilmNotFoundException If there is no film with such id.
     */
    public Film getFilmById(Long id) {
        return filmRepository.findById(id).orElseThrow(FilmNotFoundException::new);
    }

    /**
     * Get all possible film genres.
     */
    public List<Genre> getAllGenres() {
        return Arrays.asList(Genre.values());
    }

    /**
     * Get all possible film age ratings.
     */
    public List<AgeRating> getAllAgeRatings() {
        return Arrays.asList(AgeRating.values());
    }

    /**
     * Create a new film and save it to the database.
     *
     * @param filmDto Must contain all necessary fields for new Film creation.
     */
    public void createNewFilm(FilmDto filmDto) {
        for (Method method : filmDto.getClass().getDeclaredMethods()) {
            try {
                if (method.getName().startsWith("get")
                        && !method.getName().contains("Id")
                        && method.invoke(filmDto) == null) {
                    throw new FilmValidationException();
                }
            } catch (IllegalAccessException | InvocationTargetException ignored) { }
        }
    }

    /**
     * Update some fields of the film and save changes to the database.
     *
     * @param filmDto Film DTO object with updated properties.
     */
    public void updateFilm(FilmDto filmDto) {
    }

    /**
     * Remove the film from the database.
     *
     * @param id Id of the film to be removed.
     */
    public void deleteFilm(Long id) {
        filmRepository.deleteById(id);
    }
}
