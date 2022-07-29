package com.exhibitions.modernexhibitions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class YearlyLinkDto {

    private Integer source;
    private Integer target;
    private Integer startYear;
    private Integer numExhibitions;
    private String[] cities;
    private String[] countries;


}

