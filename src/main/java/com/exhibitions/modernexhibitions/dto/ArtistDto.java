package com.exhibitions.modernexhibitions.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class ArtistDto {

    private Integer id;
    private String name;
    private String nationality;
    private String sex;
    private String occupation;
    private List<ArtistDto> coArtistsOutgoing;
    private List<ArtistDto> coArtistsIncoming;
    private List<ArtistDto> coArtistsTotalOutgoing;
    private List<ArtistDto> coArtistsTotalIncoming;
}
