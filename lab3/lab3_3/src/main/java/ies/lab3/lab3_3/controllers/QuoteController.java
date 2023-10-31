package ies.lab3.lab3_3.controllers;

import ies.lab3.lab3_3.entities.Movie;
import ies.lab3.lab3_3.entities.Quote;
import ies.lab3.lab3_3.services.MovieService;
import ies.lab3.lab3_3.services.QuoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class QuoteController {

    @Autowired
    private QuoteService quoteService;
    @Autowired
    private MovieService movieService;

    @GetMapping("/quotes")
    public List<Quote> getAllQuotes() {
        return quoteService.getQuotes();
    }

    @PostMapping("/quotes/show/{id}")
    public Quote addQuote(@Valid @RequestBody Quote quote, @PathVariable(value = "id") Long movieId) {
        Movie movie = movieService.getMovieById(movieId);
        quote.setMovie(movie);
        return quoteService.saveQuote(quote);
    }

    @GetMapping("/quotes/{id}")
    public Quote getQuoteById(@PathVariable(value = "id") Long quoteId) {
        return quoteService.getQuoteById(quoteId);
    }

    @PutMapping("/quotes/{id}")
    public Quote updateQuote(@PathVariable(value = "id") Long quoteId, @Valid @RequestBody Quote quoteDetails) {
        return quoteService.updateQuote(quoteId, quoteDetails);
    }

    @DeleteMapping("/quotes/{id}")
    public String deleteQuote(@PathVariable(value = "id") Long quoteId) {
        return quoteService.deleteQuote(quoteId);
    }

    @GetMapping("/quotes/show/{id}")
    public List<Quote> getQuotesByMovieId(@PathVariable(value = "id") Long movieId) {
        return quoteService.getQuotesByMovieId(movieId);
    }

    @PostMapping("/shows")
    public Movie addShow(@Valid @RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }

    @GetMapping("/shows")
    public List<Movie> getAllShows() {
        return movieService.getMovies();
    }
}
