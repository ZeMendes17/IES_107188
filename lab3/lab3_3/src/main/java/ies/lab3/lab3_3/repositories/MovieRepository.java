package ies.lab3.lab3_3.repositories;

import ies.lab3.lab3_3.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title);
}
