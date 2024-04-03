package fhcfreitas.java.demo.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeData(@JsonAlias("Title") String title,
                          @JsonAlias("Episode") int episodeNumber,
                          @JsonAlias("imdbRating") String rating,
                          @JsonAlias("Released") String releaseDate)
{
    @Override
    public String toString() {
        return "E" + episodeNumber +
                " : " + title;
    }
}
