package com.exhibitions.modernexhibitions.entity;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.Arrays;
import java.util.List;

@RelationshipProperties
public class ExhibitsWithTotal {

    @RelationshipId
    private Long id;

    @Getter
    private final Integer[] startYears;

    @Getter
    private final Integer[] endYears;

    @Getter
    private final Integer numExhibitions;

    @Getter
    private final String[] cities;

    @Getter
    private final String[] countries;

    @TargetNode
    @Getter
    private final Artist artist;

    public ExhibitsWithTotal(Artist artist,List<Integer> startYears, List<Integer> endYears, String[] cities, String[] countries, Integer numExhibitions ) {
        this.artist = artist;
        this.startYears = Arrays.stream(startYears.toArray())
                .map(o -> (Integer) o)
                .toArray(Integer[]::new);
        this.endYears = Arrays.stream(startYears.toArray())
                .map(o -> (Integer) o)
                .toArray(Integer[]::new);
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