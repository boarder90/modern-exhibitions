package com.exhibitions.modernexhibitions.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class LinkDto {

    private Integer source;
    private Integer target;
    private Integer[] startYears;
    private Integer numExhibitions;
    private String[] cities;
    private String[] countries;

}
