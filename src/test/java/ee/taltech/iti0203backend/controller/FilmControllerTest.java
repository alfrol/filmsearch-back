package ee.taltech.iti0203backend.controller;

import ee.taltech.iti0203backend.dto.FilmDto;
import ee.taltech.iti0203backend.exception.FilmNotFoundException;
import ee.taltech.iti0203backend.model.Film;
import ee.taltech.iti0203backend.service.FilmService;
import ee.taltech.iti0203backend.utils.AgeRating;
import ee.taltech.iti0203backend.utils.Genre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FilmService service;

    private static final Long ID_1 = 10L;
    private static final String TITLE_1 = "The Matrix";
    private static final String PRODUCER_1 = "Lana Wachowski";
    private static final String PLOT_1 = "A very interesting film.";
    private static final Short DURATION_1 = 136;
    private static final Short RELEASE_DATE_1 = 1999;
    private static final String GENRE_1 = Genre.ACTION.getValue();
    private static final String AGE_RATING_1 = AgeRating.K_16.getRating();
    private static final String IMAGE_PATH_1 = "";

    private FilmDto film = new FilmDto(
            ID_1, TITLE_1, PRODUCER_1, PLOT_1, DURATION_1, RELEASE_DATE_1, GENRE_1, AGE_RATING_1, IMAGE_PATH_1
    );

    private static final Long ID_2 = 5L;
    private static final String TITLE_2 = "Schindler`s List";
    private static final String PRODUCER_2 = "Steven Spilberg";
    private static final String PLOT_2 = "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.";
    private static final Short DURATION_2 = 195;
    private static final Short RELEASE_DATE_2 = 1994;
    private static final String GENRE_2 = Genre.DRAMA.getValue();
    private static final String AGE_RATING_2 = AgeRating.K_16.getRating();
    private static final String IMAGE_PATH_2 = "";

    private FilmDto film2 = new FilmDto(
            ID_2, TITLE_2,PRODUCER_2, PLOT_2, DURATION_2, RELEASE_DATE_2, GENRE_2, AGE_RATING_2, IMAGE_PATH_2
    );

    @Test
    public void getFilms_NoRequestParams_ReturnsListOfAllFilms() throws Exception {
        when(service.getAllFilms()).thenReturn(List.of(new Film(film), new Film(film2)));

        mvc.perform(get("/films")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getFilms_ProvideRequestParam_ReturnsOnlyOneFilm() throws Exception {
        when(service.getFilmsByTitle("The Matrix")).thenReturn(List.of(new Film(film)));

        mvc.perform(get("/films").param("title", "The Matrix")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].title").value("The Matrix"));
    }

    @Test
    public void getFilms_ProvideEmptyRequestParam_ReturnsAllFilms() throws Exception {
        when(service.getAllFilms()).thenReturn(List.of(new Film(film), new Film(film2)));

        mvc.perform(get("/films").param("title", "")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void getFilm_ProvideCorrectId_ReturnsFilmWithGivenId() throws Exception {
        Film expected = new Film(film2);
        expected.setId(ID_2);
        when(service.getFilmById(ID_2)).thenReturn(expected);

        mvc.perform(get("/films/detail/{id}", ID_2)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(ID_2))
                .andExpect(jsonPath("$.title").value(TITLE_2));
    }

    @Test
    public void getFilm_ProvideIncorrectId_ThrowsException() throws Exception {
        when(service.getFilmById(0L)).thenThrow(new FilmNotFoundException());

        mvc.perform(get("/films/detail/{id}", 0L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
