package ies.WebService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MovieController {
    private final AtomicLong counter = new AtomicLong();
    private final List<Movie> movies = getMovies();

    @GetMapping("/api/quote")
    public QuoteRepresentation quote() {
        Movie movie = randomMovie();
        String quote = randomQuote(movie);
        return new QuoteRepresentation(counter.incrementAndGet(), movie.getTitle(), quote);
    }

    @GetMapping("/api/shows")
    public ShowsRepresentation shows() {
        List<String[]> s = new ArrayList<>();
        for (long id : getShows().keySet()) {
            s.add(new String[]{String.valueOf(id), getShows().get(id)});
        }
        return new ShowsRepresentation(counter.incrementAndGet(), s);
    }

    @GetMapping("/api/quotes")
    public QuotesRepresentation quotes(@RequestParam(value = "show") long show) {
        List<String> q = getQuotes(show);
        return new QuotesRepresentation(counter.incrementAndGet(), getShows().get(show), q);
    }

    private List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        AtomicLong idCounter = new AtomicLong();

        Movie movie = new Movie(idCounter.incrementAndGet(), "Batman Begins");
        movie.setQuotes(List.of("Does It Come In Black?",
                "I Assume That As You're Taking On The Underworld, This Symbol Is A Persona To Protect Those You Care About From Reprisals.",
                "It's Not Who I Am Underneath, But What I Do That Defines Me.",
                "Nice Coat.",
                "Why Do We Fall, Bruce? So We Can Learn To Pick Ourselves Up."));
        movies.add(movie);

        movie = new Movie(idCounter.incrementAndGet(), "Finding Dory");
        movie.setQuotes(List.of("I'm Sorry, Did I Forget To Mention That?",
                "I'm Not A Great Swimmer.",
                "For a guy with three hearts you're not very nice.",
                "What if I forget you? Would you ever forget me?"));
        movies.add(movie);

        movie = new Movie(idCounter.incrementAndGet(), "Goodfellas");
        movie.setQuotes(List.of("I’m funny how? Like a clown? Do I amuse you?",
                "F*** you, pay me.",
                "Never rat on your friends and always keep your mouth shut."));
        movies.add(movie);

        movie = new Movie(idCounter.incrementAndGet(), "Inception");
        movie.setQuotes(List.of("You mustn't be afraid to dream a little bigger, darling.",
                "I bought the airline. It seemed neater.",
                "They Say We Only Use A Fraction Of Our Brain’s True Potential.",
                "Admit It: You Don’t Believe In One Reality Anymore."));
        movies.add(movie);

        movie = new Movie(idCounter.incrementAndGet(), "Tangled");
        movie.setQuotes(List.of("I've got a dream!",
                "I have magic hair that glows when I sing.",
                "You were my new dream.",
                "I've got a dream!"));
        movies.add(movie);

        movie = new Movie(idCounter.incrementAndGet(), "The Princess Bride");
        movie.setQuotes(List.of("Hello. My name is Inigo Montoya. You killed my father. Prepare to die.",
                "As You Wish.",
                "Inconceivable!",
                "You keep using that word. I do not think it means what you think it means."));
        movies.add(movie);

        return movies;
    }

    private Movie randomMovie() {
        int movieIndex = (int) (Math.random() * movies.size());
        return movies.get(movieIndex);
    }
    private String randomQuote(Movie movie) {
        int quoteIndex = (int) (Math.random() * movie.getQuotes().size());
        return movie.getQuotes().get(quoteIndex);
    }

    private Map<Long, String> getShows(){
        Map<Long, String> shows = new HashMap<>();

        for (Movie movie : movies) {
            shows.put(movie.getId(), movie.getTitle());
        }
        return shows;
    }

    private List<String> getQuotes(long id){
        for (Movie movie : movies) {
            if (movie.getId() == id) {
                return movie.getQuotes();
            }
        }
        return null;
    }
}
