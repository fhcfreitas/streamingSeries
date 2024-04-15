package fhcfreitas.java.demo.controller;

import fhcfreitas.java.demo.dto.EpisodeDTO;
import fhcfreitas.java.demo.dto.SeriesDTO;
import fhcfreitas.java.demo.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/series")
public class SeriesController {
    @Autowired
    private SeriesService service;

    @GetMapping
    public List<SeriesDTO> obtainSeries() {
        return service.obtainAllSeries();
    }

    @GetMapping("/top5")
    public List<SeriesDTO> obtainTop5Series(){
        return service.obtainTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SeriesDTO> obtainTop5RecentSeries(){
        return service.obtainTop5RecentSeries();
    }

    @GetMapping("/{id}")
    public SeriesDTO obtainById(@PathVariable Long id){
        return service.obtainById(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodeDTO> obtainAllSeasons(@PathVariable Long id){
        return service.obtainAllSeasons(id);
    }

    @GetMapping("/{id}/temporadas/{number}")
    public List<EpisodeDTO> obtainEpisodesBySeason(@PathVariable Long id, @PathVariable Long number){
        return service.obtainEpisodeBySeason(id, number);
    }

    @GetMapping("/categoria/{seriesCategory}")
    public List<SeriesDTO> obtainSeriesByCategory(@PathVariable String seriesCategory){
        return service.obtainSeriesByCategory(seriesCategory);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodeDTO> obtainTop5RatedEpisodes(@PathVariable Long id){
        return service.obtainTop5Episodes(id);
    }
}


