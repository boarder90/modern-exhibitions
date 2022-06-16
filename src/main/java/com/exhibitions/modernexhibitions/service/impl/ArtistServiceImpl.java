package com.exhibitions.modernexhibitions.service.impl;

import com.exhibitions.modernexhibitions.entity.Artist;
import com.exhibitions.modernexhibitions.exception.NotFoundException;
import com.exhibitions.modernexhibitions.repository.ArtistRepository;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjection;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjectionTotalNetwork;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjectionYearlyNetwork;
import com.exhibitions.modernexhibitions.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistServiceImpl(ArtistRepository a){
        this.artistRepository = a;
    }

    @Override
    public ArtistProjection getArtistById(Integer id) throws NotFoundException {
        Optional<ArtistProjection> artistOptional = artistRepository.getArtistById(id);
        if(artistOptional.isPresent()){
            return (artistOptional.get());
        } else {
            throw new NotFoundException("Artist not found");
        }
    }

    @Override
    public List<ArtistProjection> getArtistsByIds(List<Integer> ids) {
        return artistRepository.getArtistsByByIds(ids);
    }

    @Override
    public List<ArtistProjection> getArtistsByName(String name) {
        System.out.println(name);
        System.out.println(artistRepository.getArtistsByName(name));
        return artistRepository.getArtistsByName(name);
    }

    @Override
    public List<ArtistProjection> getArtistsOfYearlyEgoNetwork(Integer id, Integer numExhibitions, Integer year) throws NotFoundException {
        this.getArtistById(id);
        return artistRepository.getArtistsOfYearlyEgoNetwork(id,numExhibitions,year);
    }

    @Override
    public List<ArtistProjection> getArtistsOfEgoNetwork(Integer id, Integer numExhibitions) throws NotFoundException {
        this.getArtistById(id);
        return artistRepository.getArtistsOfEgoNetwork(id,numExhibitions);
    }

    @Override
    public List<ArtistProjectionYearlyNetwork> getYearlyEgoNetworkOne(Integer id, Integer numExhibitions, Integer year) throws NotFoundException {
        this.getArtistById(id);
        System.out.println(artistRepository.getEgoNetworkOne(id,numExhibitions,year));
        return artistRepository.getEgoNetworkOne(id,numExhibitions,year);
    }

    @Override
    public List<ArtistProjectionYearlyNetwork> getYearlyEgoNetworkOneHalf(Integer id, Integer numExhibitions, Integer year) throws NotFoundException {
        this.getArtistById(id);
        return artistRepository.getEgoNetworkOneHalf(id,numExhibitions,year);
    }

    @Override
    public List<ArtistProjectionYearlyNetwork> getNetworkByIds(List<Integer>ids, Integer numExhibitions, Integer year) {
        return artistRepository.getNetworkByIds(ids,numExhibitions,year);
    }

    @Override
    public List<ArtistProjectionTotalNetwork> getTotalEgoNetworkOne(Integer id, Integer numExhibitions) throws NotFoundException{
        this.getArtistById(id);
        return artistRepository.getTotalEgoNetworkOne(id,numExhibitions);
    }

    @Override
    public List<ArtistProjectionTotalNetwork> getTotalEgoNetworkOneHalf(Integer id, Integer numExhibitions) throws NotFoundException{
        this.getArtistById(id);
        return artistRepository.getTotalEgoNetworkOneHalf(id,numExhibitions);
    }

    @Override
    public List<ArtistProjectionTotalNetwork> getTotalNetworkByIds(List<Integer> ids, Integer numExhibitions) {
        return artistRepository.getTotalNetworkByIds(ids,numExhibitions);
    }


}
