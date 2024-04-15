package fhcfreitas.java.demo.dto;

import fhcfreitas.java.demo.model.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record SeriesDTO(Long id,
                        String title,
                        Integer seasons,
                        Double rating,
                        Category genre,
                        String actors,
                        String poster,
                        String plot) {
}
