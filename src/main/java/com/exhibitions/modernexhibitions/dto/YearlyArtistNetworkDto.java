package com.exhibitions.modernexhibitions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Custom YearlyArtistNetworkDTO consisting of ArtistDtos as nodes and YearlyLinkDtos as links
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class YearlyArtistNetworkDto {
    List<ArtistDto> nodes;
    List<YearlyLinkDto> links;
}
