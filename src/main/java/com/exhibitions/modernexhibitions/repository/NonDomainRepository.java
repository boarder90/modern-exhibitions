package com.exhibitions.modernexhibitions.repository;

import com.exhibitions.modernexhibitions.dto.*;
import com.exhibitions.modernexhibitions.entity.Artist;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NonDomainRepository {

    FeatureCollectionDto getExhibitionLocationsAsGeoJSON(List<Integer> artistIds);

    FeatureCollectionDto getExhibitionLocationsYearlyAsGeoJSON(List<Integer> artistIds);

    List<FeatureDto> getExhibitionLocationsByEndYear(List<Integer> artistIds);

    LocationsOfNetworkDto getLocationsOfNetwork(List<Integer> artistIds);

}
