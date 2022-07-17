package com.exhibitions.modernexhibitions.service;

import com.exhibitions.modernexhibitions.dto.FeatureCollectionDto;
import com.exhibitions.modernexhibitions.dto.LocationsOfNetworkDto;

import java.util.List;

public interface NonDomainService {

    FeatureCollectionDto getExhibitionLocationsAsGeoJSON(List<Integer> artistIds);

    FeatureCollectionDto getExhibitionLocationsYearlyAsGeoJSON(List<Integer> artistIds);

    LocationsOfNetworkDto getLocationsOfNetwork(List<Integer> artistIds);


}
