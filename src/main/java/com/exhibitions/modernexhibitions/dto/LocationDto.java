package com.exhibitions.modernexhibitions.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class LocationDto {

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String venue;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private String country;

    @Getter
    @Setter
    private Double longitude;

    @Getter
    @Setter
    private Double latitude;
}
