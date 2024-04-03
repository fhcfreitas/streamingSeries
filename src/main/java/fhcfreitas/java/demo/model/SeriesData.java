package fhcfreitas.java.demo.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeriesData(@JsonAlias("Title") String title,
                         @JsonAlias("totalSeasons") Integer seasons,
                         @JsonAlias("imdbRating") String rating,
                         @JsonAlias("Genre") String genre,
                         @JsonAlias("Actors") String actors,
                         @JsonAlias("Poster") String poster,
                         @JsonAlias("Plot") String plot) {

    @Override
    public String toString() {
        return "Title = " + title +
                ", Number of Seasons = " + seasons +
                ", Plot = " + plot +
                ", Genre = " + genre +
                ", Actors = " + actors +
                ", Rating = " + rating +
                ", Poster = " + poster;
    }
}
