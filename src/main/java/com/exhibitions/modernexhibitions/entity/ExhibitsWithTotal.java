package com.exhibitions.modernexhibitions.entity;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.Arrays;
import java.util.List;

@RelationshipProperties
@Getter
public class ExhibitsWithTotal {

    @RelationshipId
    private Long id;
    private final Integer[] startYears;
    private final Integer[] endYears;
    private final Integer numExhibitions;
    private final String[] cities;
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