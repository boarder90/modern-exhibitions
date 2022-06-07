package com.exhibitions.modernexhibitions.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ArtistDto {

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String nationality;

    @Getter
    @Setter
    private String occupation;
}
