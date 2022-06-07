package com.exhibitions.modernexhibitions.dto;

import com.exhibitions.modernexhibitions.entity.Location;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
public class ExhibitionDto {

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String startDate;

    @Getter
    @Setter
    private String endDate;

    @Getter
    @Setter
    private int startYear;

    @Getter
    @Setter
    private int endYear;

    @Getter
    @Setter
    private int numArtists;

    @Getter
    @Setter
    private int numCatalogueEntries;

    @Getter
    @Setter
    List<Location> locations;

}
