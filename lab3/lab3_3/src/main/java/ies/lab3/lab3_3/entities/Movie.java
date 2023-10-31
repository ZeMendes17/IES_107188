package ies.lab3.lab3_3.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Column(name = "title", unique = true)
    private String title;

    @NotBlank(message = "Director is mandatory")
    @Column(name = "director")
    private String director;

    @NotBlank(message = "Year is mandatory")
    @Column(name = "year")
    private String year;

    @NotBlank(message = "Genre is mandatory")
    @Column(name = "genre")
    private String genre;

    // contructors
    public Movie() {
    }

    public Movie(String title, String director, String year, String genre) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.genre = genre;
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

     public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

     public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

     public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

     public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Movie{" + "id=" + id + ", title=" + title + ", director=" + director + ", year=" + year + ", genre=" + genre + '}';
    }

}
