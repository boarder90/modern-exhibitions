package com.exhibitions.modernexhibitions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * FeatureDto according to the GeoJSON standard
 */
@Getter
@Setter
public class FeatureDto {
    private final String type = "Feature";
    private PropertyDto properties;
    private GeometryDto geometry;

    public FeatureDto (PropertyDto properties, GeometryDto geometry) {
        this.properties = properties;
        this.geometry = geometry;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class PropertyDto {
        private Integer numExhibitions;
        private Integer year;
    }

    @Getter
    @Setter
    public static class GeometryDto {
        private final String type = "Point";
        private List<Double> coordinates;

        public GeometryDto (List<Double> coordinates) {
            this.coordinates = coordinates;
        }
    }
}
