package com.exhibitions.modernexhibitions.mapper;

import com.exhibitions.modernexhibitions.dto.LinkDto;
import com.exhibitions.modernexhibitions.dto.YearlyLinkDto;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjectionTotalNetwork;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjectionYearlyNetwork;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomNetworkMapper {

    public List<YearlyLinkDto> artistsToYearlyLinkDtoList(List<ArtistProjectionYearlyNetwork> artist){
        List<YearlyLinkDto> links = new ArrayList<>();
        artist.stream().forEach(a -> a.getCoArtistsIncoming().stream().forEach(e -> links.add(new YearlyLinkDto(a.getId(),e.getArtist().getId(),e.getStartYear(),e.getEndYear(),e.getNumExhibitions(),e.getCities(), e.getCountries()))));
        artist.stream().forEach(a -> a.getCoArtistsOutgoing().stream().forEach(e -> links.add(new YearlyLinkDto(a.getId(),e.getArtist().getId(),e.getStartYear(),e.getEndYear(),e.getNumExhibitions(),e.getCities(), e.getCountries()))));
        return links;
    }
    public List<LinkDto> artistsToLinkDtoList(List<ArtistProjectionTotalNetwork> artist){
        List<LinkDto> links = new ArrayList<>();
        artist.stream().forEach(a -> a.getCoArtistsTotalIncoming().stream().forEach(e -> links.add(new LinkDto(a.getId(),e.getArtist().getId(),e.getStartYears(),e.getEndYears(),e.getNumExhibitions(),e.getCities(), e.getCountries()))));
        artist.stream().forEach(a -> a.getCoArtistsTotalOutgoing().stream().forEach(e -> links.add(new LinkDto(a.getId(),e.getArtist().getId(),e.getStartYears(),e.getEndYears(),e.getNumExhibitions(),e.getCities(), e.getCountries()))));
        return links;
    }
}

