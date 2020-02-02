package ee.taltech.iti0203backend.dto;

import ee.taltech.iti0203backend.utils.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LoginDetailsDto {

    private Long id;
    private String username;
    private String token;
    private List<String> authorities;
    private Role role;
}
