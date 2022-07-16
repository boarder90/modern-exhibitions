package com.exhibitions.modernexhibitions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class ArtistNetworkAllYearsDto {

    Integer year;
    YearlyArtistNetworkDto network;
}
