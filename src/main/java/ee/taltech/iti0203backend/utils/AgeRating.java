package ee.taltech.iti0203backend.utils;

import ee.taltech.iti0203backend.exception.AgeRatingDoesNotExistException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AgeRating {

    FAMILY("Family film"),
    L("Allowed to all"),
    MS_6("Not recommended for under 6"),
    MS_12("Not recommended for under 12"),
    K_12("Prohibited for under 12 unless accompanied by an adult"),
    K_14("Prohibited for under 14 unless accompanied by an adult"),
    K_16("Prohibited for under 16 unless accompanied by an adult");

    private final String rating;

    /**
     * Return the {@code AgeRating} enum object from the provided string.
     *
     * The provided string contains the value of one of the enums and
     * based on that string we have to find the enum with that value.
     *
     * @param value The string value for searching the enum.
     * @return The enum with the value of provided string.
     * @throws AgeRatingDoesNotExistException If there is no enum with such value.
     */
    public static AgeRating fromString(String value) throws AgeRatingDoesNotExistException {
        for (AgeRating rating : AgeRating.values()) {
            if (rating.valueEquals(value)) return rating;
        }
        throw new AgeRatingDoesNotExistException("Such rating does not exist!");
    }

    private boolean valueEquals(String value) {
        return this.rating.equals(value);
    }
}
