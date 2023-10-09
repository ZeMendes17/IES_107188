package ies.WebService;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private final long id;
    private final String title;
    private List<String> quotes;

    public Movie(long id, String title) {
        this.id = id;
        this.title = title;
        this.quotes = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getQuotes() {
        return quotes;
    }

    public void addQuote(String quote) {
        quotes.add(quote);
    }

    public void setQuotes(List<String> quotes) {
        this.quotes = quotes;
    }
}
