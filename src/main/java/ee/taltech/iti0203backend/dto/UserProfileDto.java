package ee.taltech.iti0203backend.dto;

import ee.taltech.iti0203backend.model.User;
import lombok.Data;

@Data
public class UserProfileDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private Short age;
    private String email;

    public UserProfileDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.age = user.getAge();
        this.email = user.getEmail();
    }
}
