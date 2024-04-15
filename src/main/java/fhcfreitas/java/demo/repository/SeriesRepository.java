package fhcfreitas.java.demo.repository;

import fhcfreitas.java.demo.model.Category;
import fhcfreitas.java.demo.model.Episode;
import fhcfreitas.java.demo.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    Optional<Series> findByTitleContainingIgnoreCase(String seriesName);
    List<Series> findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(String actorName, Double seriesRating);
    List<Series> findTop5ByOrderByRatingDesc();
    List<Series> findByGenre(Category category);
    @Query("SELECT s FROM Series s WHERE s.seasons <= :seriesSeasons AND s.rating >= :seriesRating ORDER BY seasons ASC")
    List <Series> findBySeasonsAndRating(Integer seriesSeasons, Double seriesRating);

    @Query("SELECT e FROM Series s JOIN s.episodes e WHERE e.title ILIKE %:searchedEpisode%")
    List<Episode> episodeExcerpt(String searchedEpisode);

    @Query("SELECT e FROM Series s JOIN s.episodes e WHERE s = :series ORDER BY e.rating DESC LIMIT 5")
    List<Episode> findTop5Episodes(Series series);

    @Query("SELECT e FROM Series s JOIN s.episodes e WHERE s = :series AND YEAR(e.releaseDate) >= :searchedYear")
    List<Episode> findEpisodeByReleaseYear(Series series, int searchedYear);

    @Query("SELECT s FROM Series s " +
            "JOIN s.episodes e " +
            "GROUP BY s " +
            "ORDER BY MAX(YEAR(e.releaseDate)) DESC LIMIT 5")
    List<Series> findMostRecentReleases();

    @Query("SELECT e FROM Series s JOIN s.episodes e WHERE s.id = :id AND e.season = :number")
    List<Episode> obtainEpisodeBySeason(Long id, Long number);

}
