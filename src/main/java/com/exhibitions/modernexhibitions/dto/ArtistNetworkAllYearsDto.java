package com.exhibitions.modernexhibitions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO containing all yearly artist networks from 1904 to 1916
 */
@ToString
@Getter
@Setter
@AllArgsConstructor
public class ArtistNetworkAllYearsDto {

    Integer year;
    YearlyArtistNetworkDto network;
}
