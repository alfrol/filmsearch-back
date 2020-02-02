package ee.taltech.iti0203backend.dto;

import lombok.Data;

@Data
public class UpdateUserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private Short age;
    private String email;
    private String password;
}
