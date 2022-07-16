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

    @Transactional(readOnly = true)
    FeatureCollectionDto getExhibitionLocationsAsGeoJSON(List<Integer> artistIds);

    @Transactional(readOnly = true)
    FeatureCollectionDto getExhibitionLocationsYearlyAsGeoJSON(List<Integer> artistIds);

    @Transactional(readOnly = true)
    List<FeatureDto> getExhibitionLocationsByEndYear(List<Integer> artistIds);


}
