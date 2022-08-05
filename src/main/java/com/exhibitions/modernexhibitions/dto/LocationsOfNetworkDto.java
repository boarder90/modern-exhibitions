package com.exhibitions.modernexhibitions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Custom LocationsOfNetworkDto consisting of all the cities and countries of a network
 */
@ToString
@Getter
@Setter
@AllArgsConstructor
public class LocationsOfNetworkDto {
    private String[] cities;
    private String[] countries;
}
