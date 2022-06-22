package com.exhibitions.modernexhibitions.repository;

import com.exhibitions.modernexhibitions.dto.FeatureCollectionDto;
import com.exhibitions.modernexhibitions.dto.FeatureDto;
import com.exhibitions.modernexhibitions.dto.LinkDto;
import com.exhibitions.modernexhibitions.entity.Artist;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NonDomainRepository {

   /* @Transactional(readOnly = true)
    List<LinkDto> getEgoNetworkRelationships(Integer id, Integer numExhibitions);

    @Transactional(readOnly = true)
    List<LinkDto> getEgoNetworkOneHalfRelationships(Integer id, Integer numExhibitions);

    @Transactional(readOnly = true)
    List<LinkDto> getNetworkRelationshipsByIds(List<Integer> id, Integer numExhibitions);*/

    @Transactional(readOnly = true)
    FeatureCollectionDto getExhibitionLocationsAsGeoJSON();

    @Transactional(readOnly = true)
    FeatureCollectionDto getExhibitionLocationsYearlyAsGeoJSON();

    @Transactional(readOnly = true)
    List<FeatureDto> getExhibitionLocationsByEndYear();


}
