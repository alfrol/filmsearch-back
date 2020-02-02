package ee.taltech.iti0203backend.model;

import ee.taltech.iti0203backend.dto.RegisterDetailsDto;
import ee.taltech.iti0203backend.dto.UpdateUserDto;
import ee.taltech.iti0203backend.utils.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "user_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    private String username;
    private String password;
    private Short age;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(RegisterDetailsDto registerDetailsDto) {
        this.firstName = registerDetailsDto.getFirstName();
        this.lastName = registerDetailsDto.getLastName();
        this.username = registerDetailsDto.getUsername();
        this.password = registerDetailsDto.getPassword();
        this.age = registerDetailsDto.getAge();
        this.email = registerDetailsDto.getEmail();
        this.role = Role.USER;
    }

    public User(UpdateUserDto updateUserDto) {
        this.id = updateUserDto.getId();
        this.firstName = updateUserDto.getFirstName();
        this.lastName = updateUserDto.getLastName();
        this.username = updateUserDto.getUsername();
        this.password = updateUserDto.getPassword();
        this.age = updateUserDto.getAge();
        this.email = updateUserDto.getEmail();
    }
}
