package com.exhibitions.modernexhibitions.repository;

import com.exhibitions.modernexhibitions.dto.*;
import com.exhibitions.modernexhibitions.entity.Artist;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Non-Domain repository layer
 */
@Repository
public interface NonDomainRepository {

    /**
     *
     * @param artistIds IDs of artists
     * @return Returns a feature collection of all exhibitions
     */
    FeatureCollectionDto getExhibitionLocationsAsGeoJSON(List<Integer> artistIds);

    /**
     *
     * @param artistIds IDs of artists
     * @return Returns a feature collection of all exhibitions, attributed and ordered by years
     */
    FeatureCollectionDto getExhibitionLocationsYearlyAsGeoJSON(List<Integer> artistIds);

    /**
     * Returns exhibition locations by end year. This method is currently not used, as this would make the comparison
     * of different years very difficult
     *
     * @param artistIds IDs of artists
     * @return Returns exhibitions as FeatureDTOs
     */
    List<FeatureDto> getExhibitionLocationsByEndYear(List<Integer> artistIds);

    /**
     *
     * @param artistIds IDs of artists (nodes)
     * @return Returns locations (cities and countries) of a particular network
     */
    LocationsOfNetworkDto getLocationsOfNetwork(List<Integer> artistIds);

}
