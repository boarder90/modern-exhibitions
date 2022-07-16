package com.exhibitions.modernexhibitions.service;

import com.exhibitions.modernexhibitions.dto.FeatureCollectionDto;
import java.util.List;

public interface NonDomainService {

    FeatureCollectionDto getExhibitionLocationsAsGeoJSON(List<Integer> artistIds);

    FeatureCollectionDto getExhibitionLocationsYearlyAsGeoJSON(List<Integer> artistIds);


}
