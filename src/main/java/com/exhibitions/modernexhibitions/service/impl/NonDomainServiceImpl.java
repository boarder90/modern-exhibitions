package com.exhibitions.modernexhibitions.service.impl;

import com.exhibitions.modernexhibitions.dto.FeatureCollectionDto;
import com.exhibitions.modernexhibitions.dto.FeatureDto;
import com.exhibitions.modernexhibitions.dto.LinkDto;
import com.exhibitions.modernexhibitions.exception.NotFoundException;
import com.exhibitions.modernexhibitions.repository.NonDomainRepository;
import com.exhibitions.modernexhibitions.service.ArtistService;
import com.exhibitions.modernexhibitions.service.NonDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class NonDomainServiceImpl implements NonDomainService {

    private final NonDomainRepository nonDomainRepository;
    private final ArtistService artistService;

    @Autowired
    public NonDomainServiceImpl(NonDomainRepository nonDomainRepository, ArtistService a) {
        this.nonDomainRepository = nonDomainRepository;
        this.artistService = a;
    }


    @Override
    public List<LinkDto> getEgoNetworkRelationships(Integer id, Integer numExhibitions) throws NotFoundException {
        artistService.getArtistById(id);
        return nonDomainRepository.getEgoNetworkRelationships(id, numExhibitions);
    }

    @Override
    public List<LinkDto> getEgoNetworkOneHalfRelationships(Integer id, Integer numExhibitions) throws NotFoundException {
        artistService.getArtistById(id);
        return nonDomainRepository.getEgoNetworkOneHalfRelationships(id, numExhibitions);
    }

    @Override
    public List<LinkDto> getNetworkRelationshipsByIds(List<Integer> ids, Integer numExhibitions) {
        return nonDomainRepository.getNetworkRelationshipsByIds(ids, numExhibitions);
    }

    @Override
    public FeatureCollectionDto getExhibitionLocationsAsGeoJSON() {
        return this.nonDomainRepository.getExhibitionLocationsAsGeoJSON();
    }

    @Override
    public FeatureCollectionDto getExhibitionLocationsYearlyAsGeoJSON() {
        FeatureCollectionDto featureCollectionDto = nonDomainRepository.getExhibitionLocationsYearlyAsGeoJSON();
        List<FeatureDto> exhibitionLocationsByEndYear = nonDomainRepository.getExhibitionLocationsByEndYear();

        for (FeatureDto f : featureCollectionDto.getFeatures()) {
            for (FeatureDto e : exhibitionLocationsByEndYear) {
                if (Objects.equals(f.getGeometry().getCoordinates().get(0), e.getGeometry().getCoordinates().get(0))
                        && Objects.equals(f.getGeometry().getCoordinates().get(1), e.getGeometry().getCoordinates().get(1))
                        && Objects.equals(f.getProperties().getYear(), e.getProperties().getYear())) {
                    f.getProperties().setNumExhibitions(f.getProperties().getNumExhibitions() + e.getProperties().getNumExhibitions());
                }
            }
        }
        return featureCollectionDto;
    }
}
