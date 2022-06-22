package com.exhibitions.modernexhibitions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class LocationDto {

    private Integer id;
    private String venue;
    private String city;
    private String country;
    private Double longitude;
    private Double latitude;
}
