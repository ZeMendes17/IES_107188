package ies.lab3.lab3_3.services;

import ies.lab3.lab3_3.entities.Quote;
import ies.lab3.lab3_3.repositories.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteService {
    @Autowired
    private QuoteRepository quoteRepository;

    public Quote saveQuote(Quote quote) {
        return quoteRepository.save(quote);
    }

    public List<Quote> saveQuotes(List<Quote> quotes) {
        return quoteRepository.saveAll(quotes);
    }

    public List<Quote> getQuotes() {
        return quoteRepository.findAll();
    }

    public Quote getQuoteById(Long id) {
        return quoteRepository.findById(id).orElse(null);
    }

    public String deleteQuote(Long id) {
        quoteRepository.deleteById(id);
        return "Quote removed !! " + id;
    }

    public Quote updateQuote(Long id, Quote quote) {
        Quote existingQuote = quoteRepository.findById(id).orElse(null);
        assert existingQuote != null;
        existingQuote.setQuote(quote.getQuote());
        return quoteRepository.save(existingQuote);
    }

    public List<Quote> getQuotesByMovieId(Long movieId) {
        return quoteRepository.findByMovieId(movieId);
    }
}
