package ee.taltech.iti0203backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDetailsDto {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Short age;
    private String email;
}
