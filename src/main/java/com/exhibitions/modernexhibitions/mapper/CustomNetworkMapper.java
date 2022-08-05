package com.exhibitions.modernexhibitions.mapper;

import com.exhibitions.modernexhibitions.dto.LinkDto;
import com.exhibitions.modernexhibitions.dto.YearlyLinkDto;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjectionTotalNetwork;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjectionYearlyNetwork;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Maps the artists entities to respective link DTOs.
 */
@Component
public class CustomNetworkMapper {

    /**
     * Used for yearly networks.
     *
     * @param artists the respective artist entities
     * @return links
     */
    public List<YearlyLinkDto> artistsToYearlyLinkDtoList(List<ArtistProjectionYearlyNetwork> artists){
        List<YearlyLinkDto> links = new ArrayList<>();
        artists.stream().forEach(a -> a.getCoArtistsIncoming().stream().forEach(e -> links.add(new YearlyLinkDto(a.getId(),e.getArtist().getId(),e.getStartYear(),e.getNumExhibitions(),e.getCities(), e.getCountries()))));
        artists.stream().forEach(a -> a.getCoArtistsOutgoing().stream().forEach(e -> links.add(new YearlyLinkDto(a.getId(),e.getArtist().getId(),e.getStartYear(),e.getNumExhibitions(),e.getCities(), e.getCountries()))));
        return links;
    }

    /**
     * Used for total networks.
     *
     * @param artists the respective artist entities
     * @return links
     */
    public List<LinkDto> artistsToLinkDtoList(List<ArtistProjectionTotalNetwork> artists){
        List<LinkDto> links = new ArrayList<>();
        artists.stream().forEach(a -> a.getCoArtistsTotalIncoming().stream().forEach(e -> links.add(new LinkDto(a.getId(),e.getArtist().getId(),e.getStartYears(),e.getNumExhibitions(),e.getCities(), e.getCountries()))));
        artists.stream().forEach(a -> a.getCoArtistsTotalOutgoing().stream().forEach(e -> links.add(new LinkDto(a.getId(),e.getArtist().getId(),e.getStartYears(),e.getNumExhibitions(),e.getCities(), e.getCountries()))));
        return links;
    }
}

