package ee.taltech.iti0203backend.dao;

import ee.taltech.iti0203backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT user FROM User user WHERE user.email = :email")
    List<User> findByEmail(@Param(value = "email") String email);

    @Query("SELECT user FROM User user WHERE user.username = :username")
    List<User> findByUsername(@Param(value =  "username") String username);
}
