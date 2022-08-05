package com.exhibitions.modernexhibitions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Custom ArtistNetworkDTO consisting of ArtistDtos as nodes and LinkDtos as links
 */
@ToString
@Getter
@Setter
@AllArgsConstructor
public class ArtistNetworkDto {
    List<ArtistDto> nodes;
    List<LinkDto> links;
}
