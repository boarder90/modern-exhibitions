package com.exhibitions.modernexhibitions.entity;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
@Getter
public class ExhibitsWith {

    @RelationshipId
    private Long id;
    private final Integer startYear;
    private final Integer endYear;
    private final Integer numExhibitions;
    private final String[] cities;
    private final String[] countries;

    @TargetNode
    private final Artist artist;

    public ExhibitsWith(Artist artist, Integer startYear, Integer endYear, String[] cities, String[] countries, Integer numExhibitions ) {
        this.artist = artist;
        this.startYear = startYear;
        this.endYear = endYear;
        this.cities = cities;
        this.countries = countries;
        this.numExhibitions = numExhibitions;
    }

    @Override
    public String toString() {
        return "ExhibitsWith{" +
                "id=" + id +
                ", artist=" + artist.getName() +
                '}';
    }
}
