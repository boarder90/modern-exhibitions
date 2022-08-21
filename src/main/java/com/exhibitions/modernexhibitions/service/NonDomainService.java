package com.exhibitions.modernexhibitions.service;

import com.exhibitions.modernexhibitions.dto.FeatureCollectionDto;
import com.exhibitions.modernexhibitions.dto.LocationsOfNetworkDto;
import com.exhibitions.modernexhibitions.exception.NetworkTooLargeException;

import java.util.List;

/**
 * Service layer for calls which are not part of the application domain
 */
public interface NonDomainService {

    /**
     *
     * @param artistIds IDs of artists (nodes) to optionally filter by an artist network
     * @return Returns a list of exhibitions as a feature collection
     */
    FeatureCollectionDto getExhibitionLocationsAsGeoJSON(List<Integer> artistIds);

    /**
     *
     * @param artistIds IDs of artists (nodes) to optionally filter by an artist network
     * @return Returns a list of exhibitions as a feature collection
     */
    FeatureCollectionDto getExhibitionLocationsYearlyAsGeoJSON(List<Integer> artistIds);

    /**
     *
     * @param artistIds IDs of artists (nodes) to optionally filter by an artist network
     * @return Returns cities and countries of a network
     * @throws NetworkTooLargeException if network size exceeds the maximum size of 30 nodes
     */
    LocationsOfNetworkDto getLocationsOfNetwork(List<Integer> artistIds) throws NetworkTooLargeException;

}
