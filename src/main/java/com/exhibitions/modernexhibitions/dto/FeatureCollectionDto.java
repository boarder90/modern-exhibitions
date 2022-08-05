package com.exhibitions.modernexhibitions.dto;

import lombok.*;

import java.util.List;

/**
 * FeatureCollectionDto according to the GeoJSON standard
 */
@ToString
@Setter
@Getter
public class FeatureCollectionDto {

    private final String type = "FeatureCollection";
    private List<FeatureDto> features;

    public FeatureCollectionDto(List<FeatureDto> features){
        this.features = features;
    }

}
