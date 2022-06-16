package com.exhibitions.modernexhibitions.dto;

import com.exhibitions.modernexhibitions.entity.Location;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Setter
@Getter
public class ExhibitionDto {

    private Integer id;
    private String title;
    private String startDate;
    private String endDate;
    private int startYear;
    private int endYear;
    private int numArtists;
    private int numCatalogueEntries;
    List<LocationDto> locations;

}
