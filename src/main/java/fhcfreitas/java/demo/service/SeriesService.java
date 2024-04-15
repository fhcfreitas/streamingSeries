package fhcfreitas.java.demo.service;

import fhcfreitas.java.demo.dto.EpisodeDTO;
import fhcfreitas.java.demo.dto.SeriesDTO;
import fhcfreitas.java.demo.model.Category;
import fhcfreitas.java.demo.model.Episode;
import fhcfreitas.java.demo.model.Series;
import fhcfreitas.java.demo.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeriesService {
    @Autowired
    private SeriesRepository repository;

    private List<SeriesDTO> convertData(List<Series> series) {
        return series
                .stream()
                .map(s -> new SeriesDTO(s.getId(), s.getTitle(), s.getSeasons(), s.getRating(), s.getGenre(), s.getActors(), s.getPoster(), s.getPlot()))
                .collect(Collectors.toList());
    }

    public List<SeriesDTO> obtainAllSeries() {
        return convertData(repository.findAll());
    }

    public List<SeriesDTO> obtainTop5Series() {
        return convertData(repository.findTop5ByOrderByRatingDesc());
    }

    public List<SeriesDTO> obtainTop5RecentSeries() {
        return convertData(repository.findMostRecentReleases());
    }

    public SeriesDTO obtainById(Long id) {
        Optional<Series> series = repository.findById(id);

        if (series.isPresent()) {
            Series s = series.get();
            return new SeriesDTO(s.getId(), s.getTitle(), s.getSeasons(), s.getRating(), s.getGenre(), s.getActors(), s.getPoster(), s.getPlot());
        }
        return null;
    }

    public List<EpisodeDTO> obtainAllSeasons(Long id) {
        Optional<Series> series = repository.findById(id);

        if (series.isPresent()) {
            Series s = series.get();
            return s.getEpisodes()
                    .stream()
                    .map(e -> new EpisodeDTO(e.getSeason(), e.getEpisodeNumber(), e.getTitle()))
                    .collect(Collectors.toList());

        }
        return null;
    }

    public List<EpisodeDTO> obtainEpisodeBySeason(Long id, Long number) {
        return repository.obtainEpisodeBySeason(id, number)
                .stream()
                .map(e -> new EpisodeDTO(e.getSeason(), e.getEpisodeNumber(), e.getTitle()))
                .collect(Collectors.toList());
    }

    public List<SeriesDTO> obtainSeriesByCategory(String seriesCategory) {
        Category category = Category.fromString(seriesCategory);
        return convertData(repository.findByGenre(category));
    }

    public List<EpisodeDTO> obtainTop5Episodes(Long id) {
        Optional<Series> series = repository.findById(id);

        if (series.isPresent()) {
            Series s = series.get();
            return repository.findTop5Episodes(s)
                    .stream()
                    .map(e -> new EpisodeDTO(e.getSeason(), e.getEpisodeNumber(), e.getTitle()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}