package ee.taltech.iti0203backend.controller;

import ee.taltech.iti0203backend.dto.*;
import ee.taltech.iti0203backend.exception.CustomBadRequestException;
import ee.taltech.iti0203backend.exception.UserNotFoundException;
import ee.taltech.iti0203backend.security.JwtTokenUtil;
import ee.taltech.iti0203backend.security.SessionUser;
import ee.taltech.iti0203backend.service.CustomUserDetailsService;
import ee.taltech.iti0203backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;

    @PostMapping("/login")
    public LoginDetailsDto login(@RequestBody UserDto userDto) {
        if (userDto.getUsername() == null || userDto.getPassword() == null) {
            throw new UserNotFoundException();
        }

        final UserDetails USER_DETAILS = userDetailsService.loadUserByUsername(userDto.getUsername());
        final String TOKEN = jwtTokenUtil.generateToken(USER_DETAILS);
        SessionUser sessionUser = (SessionUser) USER_DETAILS;
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword())
        );
        return new LoginDetailsDto(sessionUser.getId(), sessionUser.getUsername(), TOKEN, getAuthorities(sessionUser), sessionUser.getRole());
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterDetailsDto registerDetailsDto) {
        if (registerDetailsDto.getUsername() == null || registerDetailsDto.getPassword() == null) {
            throw new CustomBadRequestException();
        }
        userService.createNewUser(registerDetailsDto);
    }

    @GetMapping("/{id}")
    public UserProfileDto getUserProfile(@PathVariable Long id) {
        return new UserProfileDto(userService.findById(id));
    }

    @PutMapping("/update_user")
    public UserProfileDto updateUserProfile(@RequestBody UpdateUserDto updateUserDto) {
        return userService.updateUser(updateUserDto);
    }

    @PostMapping(value = "/add_film", consumes = MediaType.APPLICATION_JSON_VALUE)
    public FilmDto addFilmToWishList(@RequestBody FilmDto filmDto) {
        return userService.addFilmToWishList(filmDto);
    }

    @GetMapping("/wishlist/{username}")
    public List<FilmDto> getUserWishlist(@PathVariable String username) {
        return userService.getWishListFilms(username);
    }

    @DeleteMapping("/delete/{username}/{id}")
    public void deleteFilmFromWishList(@PathVariable String username, @PathVariable Long id) {
        userService.deleteFromWishList(username, id);
    }

    private List<String> getAuthorities(SessionUser sessionUser) {
        return sessionUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
