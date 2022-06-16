package com.exhibitions.modernexhibitions.service;

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
    List<ArtistProjectionYearlyNetwork> getNetworkByIds(List<Integer> ids,Integer numExhibitions, Integer year);
    List<ArtistProjectionTotalNetwork> getTotalEgoNetworkOne(Integer id, Integer numExhibitions) throws NotFoundException;
    List<ArtistProjectionTotalNetwork> getTotalEgoNetworkOneHalf(Integer id, Integer numExhibitions) throws NotFoundException;
    List<ArtistProjectionTotalNetwork> getTotalNetworkByIds(List<Integer> ids, Integer numExhibitions);
}
