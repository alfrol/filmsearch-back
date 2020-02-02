package ee.taltech.iti0203backend.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Genre {

    ACTION("Action"),
    ADVENTURE("Adventure"),
    COMEDY("Comedy"),
    CRIME("Crime"),
    DRAMA("Drama"),
    HISTORICAL("Historical"),
    HORROR("Horror"),
    MUSICAL("Musical"),
    SCI_FI("Sci-Fi"),
    WAR("War"),
    WESTERN("Western");

    private final String value;
}
