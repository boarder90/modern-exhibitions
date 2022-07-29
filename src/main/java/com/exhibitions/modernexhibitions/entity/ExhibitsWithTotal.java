package com.exhibitions.modernexhibitions.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.Arrays;
import java.util.List;

@RelationshipProperties
@Getter
@Setter
public class ExhibitsWithTotal {

    @RelationshipId
    private Long id;
    private Integer[] startYears;
    private Integer numExhibitions;
    private String[] cities;
    private String[] countries;

    @TargetNode
    @Getter
    private final Artist artist;

    public ExhibitsWithTotal(Artist artist,List<Integer> startYears, String[] cities, String[] countries, Integer numExhibitions ) {
        this.artist = artist;
        this.startYears = Arrays.stream(startYears.toArray())
                .map(o -> (Integer) o)
                .toArray(Integer[]::new);
        this.cities = cities;
        this.countries = countries;
        this.numExhibitions = numExhibitions;
    }

    @Override
    public String toString() {
        return "ExhibitsWithTotal{" +
                "id=" + id +
                ", artist=" + artist.getName() +
                '}';
    }


}