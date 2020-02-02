package ee.taltech.iti0203backend.controller;

import ee.taltech.iti0203backend.dto.FilmDto;
import ee.taltech.iti0203backend.dto.MainPageFilmDto;
import ee.taltech.iti0203backend.service.FilmService;
import ee.taltech.iti0203backend.utils.AgeRating;
import ee.taltech.iti0203backend.utils.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class FilmController {

    private final FilmService filmService;

    /**
     * Get all films from the database using {@link FilmService}.
     *
     * If necessary, use optional argument {@code title} in order
     * to find a film with similar title.
     *
     * @param title Optional argument, defines title of the film.
     * @return List of films.
     */
    @GetMapping
    public List<MainPageFilmDto> getFilms(@RequestParam(required = false) String title) {
        if (!StringUtils.isEmpty(title)) {
            return filmService.getFilmsByTitle(title).stream()
                    .map(MainPageFilmDto::new)
                    .collect(Collectors.toList());
        }
        return filmService.getAllFilms().stream()
                .map(MainPageFilmDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Get film by its id.
     *
     * @param id The id of the specific film (unique).
     * @return Film with provided id.
     */
    @GetMapping("/detail/{id}")
    public FilmDto getFilm(@PathVariable Long id) {
        return new FilmDto(filmService.getFilmById(id));
    }

    /**
     * GET all possible film genres.
     */
    @GetMapping("/filter/genres")
    public List<Genre> getAllGenres() {
        return this.filmService.getAllGenres();
    }

    /**
     * GET all possible age ratings.
     */
    @GetMapping("/filter/age-ratings")
    public List<AgeRating> getAllAgeRatings() {
        return this.filmService.getAllAgeRatings();
    }

    /**
     * POST or save a new film to the database.
     *
     * @param filmDto A new film to be saved.
     * @return The film after it has been successfully saved.
     */
    @PostMapping
    public FilmDto saveFilm(@RequestBody FilmDto filmDto) {
        filmService.createNewFilm(filmDto);
        return filmDto;
    }

    /**
     * PUT or update the film information.
     *
     * @param filmDto Film DTO object which contains all values
     *                which must be changed.
     * @return The film after it has been successfully changed.
     */
    @PutMapping
    public FilmDto updateFilm(@RequestBody FilmDto filmDto) {
        filmService.updateFilm(filmDto);
        return filmDto;
    }

    /**
     * DELETE film from the database.
     *
     * @param id The id of the film to be deleted.
     */
    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Long id)  {
        filmService.deleteFilm(id);
    }
}
