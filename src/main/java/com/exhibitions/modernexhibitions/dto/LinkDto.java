package com.exhibitions.modernexhibitions.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class LinkDto {

    private Integer startId;
    private Integer endId;
    private Integer[] startYears;
    private Integer[] endYears;
    private Integer numExhibitions;
    private String[] cities;
    private String[] countries;

}
