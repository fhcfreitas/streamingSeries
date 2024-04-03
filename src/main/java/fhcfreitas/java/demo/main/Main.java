package fhcfreitas.java.demo.main;

import ch.qos.logback.core.encoder.JsonEscapeUtil;
import fhcfreitas.java.demo.model.*;
import fhcfreitas.java.demo.repository.SeriesRepository;
import fhcfreitas.java.demo.service.ConsumingAPI;
import fhcfreitas.java.demo.service.ConvertData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private Scanner input = new Scanner(System.in);
    private ConsumingAPI consume = new ConsumingAPI();
    private ConvertData converter = new ConvertData();
    private final String WEBSITE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=da587032";
    private List<SeriesData> seriesData = new ArrayList<>();
    private SeriesRepository repository;
    private List<Series> seriesList = new ArrayList<>();
    Optional<Series> searchedSeries;

    public Main(SeriesRepository repository) {
        this.repository = repository;

    }

    public void menu() {
        var option = -1;
        while(option != 0) {
            var menu = """
                    \nMenu
                    1. Search Series
                    2. Search Episodes
                    3. List of searched series
                    4. Search Series by Title
                    5. Search Series by Artist
                    6. Show top 5 ranked series
                    7. Search Series by Genre
                    8. Search Series by Amount of Seasons
                    9. Search for an Episode
                    10. Search top 5  ranked episodes from series
                    11. Search for episodes by its release Year
                                
                    0. Log out              
                    
                    Type an option:
                    """;

            System.out.println(menu);
            option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1:
                    searchSeries();
                    break;
                case 2:
                    searchEpisodeBySeries();
                    break;
                case 3:
                    searchedSeriesList();
                    break;
                case 4:
                    searchSeriesByTitle();
                    break;
                case 5:
                    searchSeriesByActor();
                    break;
                case 6:
                    searchTop5RankedSeries();
                    break;
                case 7:
                    searchSeriesBYGenre();
                    break;
                case 8:
                    searchSeriesByAmountOfSeasons();
                    break;
                case 9:
                    searchAnEpisode();
                    break;
                case 10:
                    searchTop5RankedEpisodes();
                    break;
                case 11:
                    searchEpisodeByYear();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid Option");
            }
        }
    }

    private SeriesData getSeriesData() {
        System.out.println("Search for a TV Series:");
        var seriesName = input.nextLine();
        var json = consume.obtainData(WEBSITE + seriesName.replace(" ", "+") + API_KEY);
        SeriesData data = converter.obtainData(json, SeriesData.class);
        return data;
    }

    private void searchSeries() {
        SeriesData data = getSeriesData();
        Series series = new Series(data);

        repository.save(series);
        System.out.println(data);
    }

    private void searchEpisodeBySeries(){
        searchedSeriesList();
        System.out.println("Type the name of the series");
        var seriesName = input.nextLine();

        Optional<Series> series = repository.findByTitleContainingIgnoreCase(seriesName);

        if (series.isPresent()) {
            var foundSeries = series.get();
            List<SeasonData> seasons = new ArrayList<>();

            for (int i = 1; i <= foundSeries.getSeasons(); i++) {
                var json = consume.obtainData(WEBSITE + foundSeries.getTitle().replace(" ", "+") + "&season=" + i + API_KEY);
                SeasonData seasonData = converter.obtainData(json, SeasonData.class);
                seasons.add(seasonData);
            }
            seasons.forEach(System.out::println);

            List<Episode> episodes = seasons.stream()
                    .flatMap(d -> d.episodes().stream()
                            .map(e -> new Episode(d.season(), e)))
                    .collect(Collectors.toList());
            foundSeries.setEpisodes(episodes);
            repository.save(foundSeries);
        } else {
            System.out.println("Series not found.");
        }
    }

    private void searchedSeriesList(){
        seriesList = repository.findAll();
        seriesList.stream()
                .sorted(Comparator.comparing(Series::getGenre))
                .forEach(System.out::println);
    }

    private void searchSeriesByTitle() {
        System.out.println("Type the name of the series:");
        var seriesName = input.nextLine();

        searchedSeries = repository.findByTitleContainingIgnoreCase(seriesName);

        if (searchedSeries.isPresent()){
            System.out.println(searchedSeries.get());
        } else {
            System.out.println("Series not found.");
        }
    }

    private void searchSeriesByActor() {
        System.out.println("Type the name of the artist:");
        var actorName = input.nextLine();
        System.out.println("Rating from: ");
        var seriesRating = input.nextDouble();

        List<Series> actorSeries = repository.findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(actorName, seriesRating);
        System.out.println("Series with " + actorName + " :");
        actorSeries.forEach(s ->
                System.out.println(s.getTitle() + " - Seasons : " + s.getSeasons()));
    }

    private void searchTop5RankedSeries() {
        List<Series> topRankedSeries = repository.findTop5ByOrderByRatingDesc();
        System.out.println("Top 5 ranked series");
        topRankedSeries.forEach(s ->
                System.out.println(s.getTitle() + " - Rating: " + s.getRating()));
    }

    private void searchSeriesBYGenre() {
        System.out.println("What series genre do you want to watch?");
        var seriesGenre = input.nextLine();
        Category category = Category.fromString(seriesGenre);

        List<Series> seriesGenreList = repository.findByGenre(category);
        seriesGenreList.forEach(s ->
                System.out.println(s.getTitle() + " - Genre: " + s.getGenre()));
    }

    private void searchSeriesByAmountOfSeasons() {
        System.out.println("How many seasons can the series have?");
        var numberOfSeasons = input.nextInt();
        System.out.println("Rating from: ");
        var seriesRating = input.nextDouble();

        List<Series> seriesWithSeasons = repository.findBySeasonsAndRating(numberOfSeasons, seriesRating);
        System.out.println("List of series with " + numberOfSeasons + " seasons");
        seriesWithSeasons.forEach(s ->
                System.out.println(s.getTitle() + " - Seasons: " + s.getSeasons() + " - Rating: " + s.getRating()));

    }

    private void searchAnEpisode() {
        System.out.println("What episode do you want to watch?");
        var searchedEpisode = input.nextLine();

        List<Episode> searchedEpisodeList = repository.episodeExcerpt(searchedEpisode);
        searchedEpisodeList.forEach(e ->
                System.out.printf("Series: %s - S%sE%s: %s\n",
                        e.getSeries().getTitle(), e.getSeason(), e.getEpisodeNumber(), e.getTitle()));
     }

    private void searchTop5RankedEpisodes() {
        searchSeriesByTitle();
        if (searchedSeries.isPresent()){
            Series series = searchedSeries.get();
            List<Episode> topRankedEpisodes = repository.findTop5Episodes(series);
            topRankedEpisodes.forEach(e ->
                    System.out.printf("S%sE%s: %s - Rating: %s\n",
                            e.getSeason(), e.getEpisodeNumber(), e.getTitle(), e.getRating()));
        }
    }

    private void searchEpisodeByYear() {
        searchSeriesByTitle();
        if (searchedSeries.isPresent()){
            Series series = searchedSeries.get();
            System.out.println("Episodes from which date?");
            var searchedYear = input.nextInt();

            List<Episode> episodesYear = repository.findEpisodeByReleaseYear(series, searchedYear);
            episodesYear.forEach(System.out::println);
        }
    }

}