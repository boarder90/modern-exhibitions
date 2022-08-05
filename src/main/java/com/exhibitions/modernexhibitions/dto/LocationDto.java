package com.exhibitions.modernexhibitions.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * LocationDto
 */
@ToString
@Getter
@Setter
public class LocationDto {

    private Integer id;
    private String city;
    private String country;
    private Double longitude;
    private Double latitude;
}
