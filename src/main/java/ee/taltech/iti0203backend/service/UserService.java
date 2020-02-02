package ee.taltech.iti0203backend.service;

import ee.taltech.iti0203backend.dao.FilmRepository;
import ee.taltech.iti0203backend.dao.UserRepository;
import ee.taltech.iti0203backend.dao.WishListRepository;
import ee.taltech.iti0203backend.dto.*;
import ee.taltech.iti0203backend.exception.UserNotFoundException;
import ee.taltech.iti0203backend.exception.UserValidationError;
import ee.taltech.iti0203backend.model.Film;
import ee.taltech.iti0203backend.model.User;
import ee.taltech.iti0203backend.model.WishList;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final WishListRepository wishListRepository;
    private final FilmRepository filmRepository;
    private final BCryptPasswordEncoder encoder;

    public List<FilmDto> getWishListFilms(String username) {
        List<WishList> wishList = wishListRepository.findFilmsForUser(username);
        List<FilmDto> films = new ArrayList<>();

        for (WishList w : wishList) {
            Film f = filmRepository.findById(w.getFilmId()).get();
            films.add(new FilmDto(f));
        }
        return films;
    }

    public void deleteFromWishList(String username, Long id) {
        System.out.println("hello");
        wishListRepository.deleteFilm(username, id);
    }

    public User findByUsername(String username) throws UserNotFoundException {
        List<User> users = userRepository.findByUsername(username);

        if (users.size() == 0) {
            throw new UserNotFoundException("Username does not exist");
        }
        return userRepository.findByUsername(username).get(0);
    }

    public List<User> findByEmail(String email) throws UserNotFoundException {
        List<User> users = userRepository.findByEmail(email);

        if (users.size() == 0) {
            throw new UserNotFoundException("Email is not registered");
        }
        return userRepository.findByEmail(email);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public void createNewUser(RegisterDetailsDto registerDetailsDto) {
        for (Method method : registerDetailsDto.getClass().getDeclaredMethods()) {
            try {
                if (method.getName().startsWith("get")
                        && method.invoke(registerDetailsDto) == null) {
                    throw new UserValidationError();
                }
            } catch (IllegalAccessException | InvocationTargetException ignored) {}
        }

        try {
            if (findByUsername(registerDetailsDto.getUsername()) != null) {
                throw new UserValidationError("Username already exists");
            }
            if (findByEmail(registerDetailsDto.getEmail()) != null) {
                throw new UserValidationError("Email already registered");
            }
        } catch (UserNotFoundException ignored) {}

        final String ENCODED_PASSWORD = encoder.encode(registerDetailsDto.getPassword());
        registerDetailsDto.setPassword(ENCODED_PASSWORD);
        userRepository.save(new User(registerDetailsDto));
    }

    public FilmDto addFilmToWishList(FilmDto filmDto) {
        String currentUserUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        WishList wishlist = new WishList(currentUserUsername, filmDto.getId());
        List<WishList> allWishLists = wishListRepository.findAll();

        boolean notExists = allWishLists.stream()
                .noneMatch(wishList -> wishList.getUsername().equals(currentUserUsername)
                        && wishList.getFilmId().equals(filmDto.getId()));

        if (notExists) {
            wishListRepository.save(wishlist);
            return filmDto;
        }
        return null;
    }

    public UserProfileDto updateUser(UpdateUserDto updateUserDto) {
        User user = new User(updateUserDto);
        User old = findById(updateUserDto.getId());
        user.setRole(old.getRole());

        if (findByUsername(user.getUsername()) != null && !user.getUsername().equals(old.getUsername())) {
            throw new UserValidationError("Username already exists");
        }
        if (findByEmail(user.getEmail()) != null && !user.getEmail().equals(old.getEmail())) {
            throw new UserValidationError("Email already registered");
        }

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            final String ENCODED_PASSWORD = encoder.encode(user.getPassword());
            user.setPassword(ENCODED_PASSWORD);
        } else {
            user.setPassword(old.getPassword());
        }
        userRepository.save(user);

        return new UserProfileDto(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
