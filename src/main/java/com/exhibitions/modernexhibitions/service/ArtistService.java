package com.exhibitions.modernexhibitions.service;

import com.exhibitions.modernexhibitions.exception.NetworkTooLargeException;
import com.exhibitions.modernexhibitions.exception.NotFoundException;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjection;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjectionTotalNetwork;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjectionYearlyNetwork;

import java.util.List;

/**
 * Service layer for artists
 */
public interface ArtistService {

    /**
     *
     * @param id ID of the artist
     * @return Returns Artist as ArtistProjection
     * @throws NotFoundException if artist does not exist in the database
     */
    ArtistProjection getArtistById(Integer id) throws NotFoundException;

    /**
     *
     * @param ids IDs of the artists
     * @return Returns artists as a list of ArtistProjections by IDs
     */
    List<ArtistProjection> getArtistsByIds(List<Integer> ids);

    /**
     *
     * @param name name of the artist
     * @return Returns artists as a list of ArtistProjections by (part of) name
     */
    List<ArtistProjection> getArtistsByName(String name);

    /**
     *
     * @param ids ID of the artists
     * @param numExhibitions minimum number of exhibitions (optional filter)
     * @param year year of the ego network (optional filter)
     * @return Returns artists as a list of ArtistProjections
     * @throws NotFoundException if artist does not exist in the database
     */
    List<ArtistProjection> getArtistsOfYearlyEgoNetwork(Integer ids,Integer numExhibitions,Integer year) throws NotFoundException;

    /**
     *
     * @param id ID of the artist
     * @param numExhibitions minimum number of exhibitions (optional filter)
     * @return Returns artists as a list of ArtistProjections
     * @throws NotFoundException if artist does not exist in the database
     */
    List<ArtistProjection> getArtistsOfEgoNetwork(Integer id,Integer numExhibitions) throws NotFoundException;

    /**
     *
     * @param id ID of the artist
     * @param numExhibitions minimum number of exhibitions (optional filter)
     * @param year year of the ego network (optional filter)
     * @return Returns a list of ArtistProjectionYearlyNetworks
     * @throws NotFoundException if artist does not exist in the database
     */
    List<ArtistProjectionYearlyNetwork> getYearlyEgoNetworkOne(Integer id, Integer numExhibitions, Integer year) throws NotFoundException;

    /**
     *
     * @param id ID of the artist
     * @param numExhibitions minimum number of exhibitions (optional filter)
     * @param year year of the ego network (optional filter)
     * @return Returns a list of ArtistProjectionYearlyNetworks
     * @throws NotFoundException if artist does not exist in the database
     */
    List<ArtistProjectionYearlyNetwork> getYearlyEgoNetworkOneHalf(Integer id, Integer numExhibitions, Integer year) throws NotFoundException;

    /**
     *
     * @param ids IDs of the artists
     * @param numExhibitions minimum number of exhibitions (optional filter)
     * @param year year of the network (optional filter)
     * @return Returns a list of ArtistProjectionYearlyNetworks
     * @throws NetworkTooLargeException if network size exceeds the maximum size of 30 nodes
     */
    List<ArtistProjectionYearlyNetwork> getYearlyNetworkByIds(List<Integer> ids,Integer numExhibitions, Integer year) throws NetworkTooLargeException;

    /**
     *
     * @param id ID of the artist
     * @param numExhibitions minimum number of exhibitions (optional filter)
     * @return Returns a list of ArtistProjectionTotalNetworks
     * @throws NotFoundException if artist does not exist in the database
     */
    List<ArtistProjectionTotalNetwork> getTotalEgoNetworkOne(Integer id, Integer numExhibitions) throws NotFoundException;

    /**
     *
     * @param id ID of the artist
     * @param numExhibitions minimum number of exhibitions (optional filter)
     * @return Returns a list of ArtistProjectionTotalNetworks
     * @throws NotFoundException if artist does not exist in the database
     */
    List<ArtistProjectionTotalNetwork> getTotalEgoNetworkOneHalf(Integer id, Integer numExhibitions) throws NotFoundException;

    /**
     *
     * @param ids IDs of the artists
     * @param numExhibitions minimum number of exhibitions (optional filter)
     * @return Returns a list of ArtistProjectionTotalNetworks
     * @throws NetworkTooLargeException if network size exceeds the maximum size of 30 nodes
     */
    List<ArtistProjectionTotalNetwork> getTotalNetworkByIds(List<Integer> ids, Integer numExhibitions) throws NetworkTooLargeException;

    /**
     *
     * @param ids IDs of the artists
     * @param country country code
     * @return Returns a list of ArtistProjectionTotalNetworks
     * @throws NetworkTooLargeException if network size exceeds the maximum size of 30 nodes
     */
    List<ArtistProjectionTotalNetwork> getTotalNetworkByIdsFilteredByCountry(List<Integer> ids, String country) throws NetworkTooLargeException;

    /**
     *
     * @param ids IDs of the artists
     * @param city city name
     * @return Returns a list of ArtistProjectionTotalNetworks
     * @throws NetworkTooLargeException if network size exceeds the maximum size of 30 nodes
     */
    List<ArtistProjectionTotalNetwork> getTotalNetworkByIdsFilteredByCity(List<Integer> ids, String city) throws NetworkTooLargeException;

    /**
     *
     * @param ids IDs of the artists
     * @param country country code
     * @param year year of the network
     * @return Returns a list of ArtistProjectionYearlyNetworks
     * @throws NetworkTooLargeException if network size exceeds the maximum size of 30 nodes
     */
    List<ArtistProjectionYearlyNetwork> getYearlyNetworkByIdsFilteredByCountry(List<Integer> ids, String country, Integer year) throws NetworkTooLargeException;

    /**
     *
     * @param ids IDs of the artists
     * @param city city name
     * @param year year of the network
     * @return Returns a list of ArtistProjectionYearlyNetworks
     * @throws NetworkTooLargeException if network size exceeds the maximum size of 30 nodes
     */
    List<ArtistProjectionYearlyNetwork> getYearlyNetworkByIdsFilteredByCity(List<Integer> ids, String city, Integer year) throws NetworkTooLargeException;

}
