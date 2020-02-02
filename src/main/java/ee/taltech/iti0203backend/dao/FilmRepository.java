package ee.taltech.iti0203backend.dao;

import ee.taltech.iti0203backend.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    @Query("SELECT film FROM Film film WHERE LOWER(film.title) LIKE LOWER( CONCAT('%', :title, '%') )")
    List<Film> findAllByTitle(@Param(value = "title") String title);
}
