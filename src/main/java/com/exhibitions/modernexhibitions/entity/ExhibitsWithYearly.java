package com.exhibitions.modernexhibitions.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
@Getter
@Setter
public class ExhibitsWithYearly {

    @RelationshipId
    private Long id;
    private Integer startYear;
    private Integer numExhibitions;
    private String[] cities;
    private String[] countries;

    @TargetNode
    private final Artist artist;

    public ExhibitsWithYearly(Artist artist, Integer startYear, String[] cities, String[] countries, Integer numExhibitions ) {
        this.artist = artist;
        this.startYear = startYear;
        this.cities = cities;
        this.countries = countries;
        this.numExhibitions = numExhibitions;
    }

    @Override
    public String toString() {
        return "ExhibitsWithYearly{" +
                "id=" + id +
                ", artist=" + artist.getName() +
                '}';
    }
}
