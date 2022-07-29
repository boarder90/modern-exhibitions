package com.exhibitions.modernexhibitions.service;

import com.exhibitions.modernexhibitions.exception.NetworkTooLargeException;
import com.exhibitions.modernexhibitions.exception.NotFoundException;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjection;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjectionTotalNetwork;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjectionYearlyNetwork;

import java.util.List;

public interface ArtistService {

    ArtistProjection getArtistById(Integer id) throws NotFoundException;
    List<ArtistProjection> getArtistsByIds(List<Integer> id);
    List<ArtistProjection> getArtistsByName(String name);
    List<ArtistProjection> getArtistsOfYearlyEgoNetwork(Integer id,Integer numExhibitions,Integer year) throws NotFoundException;
    List<ArtistProjection> getArtistsOfEgoNetwork(Integer id,Integer numExhibitions) throws NotFoundException;
    List<ArtistProjectionYearlyNetwork> getYearlyEgoNetworkOne(Integer id, Integer numExhibitions, Integer year) throws NotFoundException;
    List<ArtistProjectionYearlyNetwork> getYearlyEgoNetworkOneHalf(Integer id, Integer numExhibitions, Integer year) throws NotFoundException;
    List<ArtistProjectionYearlyNetwork> getYearlyNetworkByIds(List<Integer> ids,Integer numExhibitions, Integer year) throws NetworkTooLargeException;
    List<ArtistProjectionTotalNetwork> getTotalEgoNetworkOne(Integer id, Integer numExhibitions) throws NotFoundException;
    List<ArtistProjectionTotalNetwork> getTotalEgoNetworkOneHalf(Integer id, Integer numExhibitions) throws NotFoundException;
    List<ArtistProjectionTotalNetwork> getTotalNetworkByIds(List<Integer> ids, Integer numExhibitions) throws NetworkTooLargeException;
    List<ArtistProjectionTotalNetwork> getTotalNetworkByIdsFilteredByCountry(List<Integer> ids, String country) throws NetworkTooLargeException;
    List<ArtistProjectionTotalNetwork> getTotalNetworkByIdsFilteredByCity(List<Integer> ids, String city) throws NetworkTooLargeException;
    List<ArtistProjectionYearlyNetwork> getYearlyNetworkByIdsFilteredByCountry(List<Integer> ids, String country, Integer year) throws NetworkTooLargeException;
    List<ArtistProjectionYearlyNetwork> getYearlyNetworkByIdsFilteredByCity(List<Integer> ids, String city, Integer year) throws NetworkTooLargeException;

}
