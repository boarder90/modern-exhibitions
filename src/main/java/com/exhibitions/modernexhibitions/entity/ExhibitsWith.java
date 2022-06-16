package com.exhibitions.modernexhibitions.entity;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
public class ExhibitsWith {

    @RelationshipId
    private Long id;

    @Getter
    private final Integer startYear;

    @Getter
    private final Integer endYear;

    @Getter
    private final Integer numExhibitions;

    @Getter
    private final String[] cities;

    @Getter
    private final String[] countries;

    @TargetNode
    @Getter
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
