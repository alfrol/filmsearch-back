package ee.taltech.iti0203backend.exception;

public class AgeRatingDoesNotExistException extends RuntimeException {

    public AgeRatingDoesNotExistException(String message) {
        super(message);
    }
}
