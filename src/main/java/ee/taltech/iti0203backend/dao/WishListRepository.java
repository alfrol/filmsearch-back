package ee.taltech.iti0203backend.dao;

import ee.taltech.iti0203backend.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    @Query("SELECT wishlist FROM WishList wishlist WHERE wishlist.username = :username")
    List<WishList> findFilmsForUser(@Param(value = "username") String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM WishList wishlist WHERE wishlist.username = :username AND wishlist.filmId = :id")
    void deleteFilm(@Param(value = "username") String username, @Param(value = "id") Long id);
}
