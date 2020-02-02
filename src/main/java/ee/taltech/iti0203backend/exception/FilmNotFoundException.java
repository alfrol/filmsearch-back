package ee.taltech.iti0203backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception has to be thrown every time the client requests
 * a film which does not exist in the database.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Film Not Found")
public class FilmNotFoundException extends RuntimeException { }
