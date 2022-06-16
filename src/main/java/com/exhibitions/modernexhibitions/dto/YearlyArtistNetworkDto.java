package com.exhibitions.modernexhibitions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class YearlyArtistNetworkDto {
    List<ArtistDto> nodes;
    List<YearlyLinkDto> links;
}
