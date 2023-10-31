package ies.lab3.lab3_3.repositories;

import ies.lab3.lab3_3.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findByMovieId(Long movieId);
}
