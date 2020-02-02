package ee.taltech.iti0203backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception has to be thrown every time the user tries
 * to save the film or update some values of the film and leaves
 * some of them unfilled.
 *
 * ALL OF THE PROPERTIES MUST BE FILLED OUT.
 * THERE CANNOT BE EMPTY/UNDEFINED PROPERTIES.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Film Validation Error")
public class FilmValidationException extends RuntimeException {
}
