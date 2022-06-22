package com.exhibitions.modernexhibitions.service;

import com.exhibitions.modernexhibitions.dto.FeatureCollectionDto;
import com.exhibitions.modernexhibitions.dto.LinkDto;
import com.exhibitions.modernexhibitions.exception.NotFoundException;

import java.util.List;

public interface NonDomainService {

    List<LinkDto> getEgoNetworkRelationships(Integer id, Integer numExhibitions) throws NotFoundException;

    List<LinkDto> getEgoNetworkOneHalfRelationships(Integer id, Integer numExhibitions) throws NotFoundException;

    List<LinkDto> getNetworkRelationshipsByIds(List<Integer> ids, Integer numExhibitions);

    FeatureCollectionDto getExhibitionLocationsAsGeoJSON();

    FeatureCollectionDto getExhibitionLocationsYearlyAsGeoJSON();


}
