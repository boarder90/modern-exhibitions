package com.exhibitions.modernexhibitions.service.impl;

import com.exhibitions.modernexhibitions.entity.ExhibitsWithYearly;
import com.exhibitions.modernexhibitions.entity.ExhibitsWithTotal;
import com.exhibitions.modernexhibitions.exception.NetworkTooLargeException;
import com.exhibitions.modernexhibitions.exception.NotFoundException;
import com.exhibitions.modernexhibitions.repository.ArtistRepository;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjection;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjectionTotalNetwork;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjectionYearlyNetwork;
import com.exhibitions.modernexhibitions.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
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
    public List<ArtistProjectionYearlyNetwork> getYearlyNetworkByIds(List<Integer>ids, Integer numExhibitions, Integer year) throws NetworkTooLargeException{
        if(ids!= null && ids.size()>30){
            throw new NetworkTooLargeException("Currently only 30 distinctive ids are supported.");
        }
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
    public List<ArtistProjectionTotalNetwork> getTotalNetworkByIds(List<Integer> ids, Integer numExhibitions) throws NetworkTooLargeException {
        if(ids!= null && ids.size()>30){
            throw new NetworkTooLargeException("Currently only 30 distinctive ids are supported.");
        }
        return artistRepository.getTotalNetworkByIds(ids,numExhibitions);
    }

    @Override
    public List<ArtistProjectionTotalNetwork> getTotalNetworkByIdsFilteredByCountry(List<Integer> ids, String country) throws NetworkTooLargeException{
        if(ids!= null && ids.size()>30){
            throw new NetworkTooLargeException("Currently only 30 distinctive ids are supported.");
        }
        List<ArtistProjectionTotalNetwork> artists  = artistRepository.getTotalNetworkByIdsFilteredByCountry(ids, country);
        for (ArtistProjectionTotalNetwork a: artists) {
            for (int i=0; i <a.getCoArtistsTotalIncoming().toArray().length;i++){
                a.getCoArtistsTotalIncoming().toArray()[i] = filterLinksTotal((ExhibitsWithTotal) a.getCoArtistsTotalIncoming().toArray()[i],country,"country");
            }
            for (int i=0;i < a.getCoArtistsTotalOutgoing().toArray().length; i++){
                a.getCoArtistsTotalOutgoing().toArray()[i]  = filterLinksTotal((ExhibitsWithTotal) a.getCoArtistsTotalOutgoing().toArray()[i], country,"country");
            }
        }
        return artists;
    }

    @Override
    public List<ArtistProjectionTotalNetwork> getTotalNetworkByIdsFilteredByCity(List<Integer> ids, String city) throws NetworkTooLargeException{
        if(ids!= null && ids.size()>30){
            throw new NetworkTooLargeException("Currently only 30 distinctive ids are supported.");
        }
        List<ArtistProjectionTotalNetwork> artists  = artistRepository.getTotalNetworkByIdsFilteredByCity(ids, city);
        for (ArtistProjectionTotalNetwork a: artists) {
            for (int i=0; i <a.getCoArtistsTotalIncoming().toArray().length;i++){
                a.getCoArtistsTotalIncoming().toArray()[i] = filterLinksTotal((ExhibitsWithTotal) a.getCoArtistsTotalIncoming().toArray()[i],city,"city");
            }
            for (int i=0;i < a.getCoArtistsTotalOutgoing().toArray().length; i++){
                a.getCoArtistsTotalOutgoing().toArray()[i]  = filterLinksTotal((ExhibitsWithTotal) a.getCoArtistsTotalOutgoing().toArray()[i], city,"city");
            }
        }

        return artists;
    }

    @Override
    public List<ArtistProjectionYearlyNetwork> getYearlyNetworkByIdsFilteredByCountry(List<Integer> ids, String country, Integer year) throws NetworkTooLargeException {
        if(ids!= null && ids.size()>30){
            throw new NetworkTooLargeException("Currently only 30 distinctive ids are supported.");
        }
        List<ArtistProjectionYearlyNetwork> artists  = artistRepository.getYearlyNetworkByIdsFilteredByCountry(ids, country, year);

        for (ArtistProjectionYearlyNetwork a: artists) {
            for (int i=0; i <a.getCoArtistsIncoming().toArray().length;i++){
                a.getCoArtistsIncoming().toArray()[i] = filterLinksYearly((ExhibitsWithYearly) a.getCoArtistsIncoming().toArray()[i],country,"country");
            }
            for (int i=0;i < a.getCoArtistsOutgoing().toArray().length; i++){
                a.getCoArtistsOutgoing().toArray()[i]  = filterLinksYearly((ExhibitsWithYearly) a.getCoArtistsOutgoing().toArray()[i], country,"country");
            }
        }

        return artists;
    }

    @Override
    public List<ArtistProjectionYearlyNetwork> getYearlyNetworkByIdsFilteredByCity(List<Integer> ids, String city, Integer year) throws NetworkTooLargeException{
        if(ids!= null && ids.size()>30){
            throw new NetworkTooLargeException("Currently only 30 distinctive ids are supported.");
        }
        List<ArtistProjectionYearlyNetwork> artists  = artistRepository.getYearlyNetworkByIdsFilteredByCity(ids, city, year);

        for (ArtistProjectionYearlyNetwork a: artists) {
            for (int i=0; i <a.getCoArtistsIncoming().toArray().length;i++){
                a.getCoArtistsIncoming().toArray()[i] = filterLinksYearly((ExhibitsWithYearly) a.getCoArtistsIncoming().toArray()[i],city,"city");
            }
            for (int i=0;i < a.getCoArtistsOutgoing().toArray().length; i++){
                a.getCoArtistsOutgoing().toArray()[i]  = filterLinksYearly((ExhibitsWithYearly) a.getCoArtistsOutgoing().toArray()[i], city,"city");
            }
        }
        return artists;
    }

    private ExhibitsWithTotal filterLinksTotal(ExhibitsWithTotal e, String location, String granularity){
        ArrayList<String> countries = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();
        ArrayList<Integer> startYears = new ArrayList<>();
        int numExhibitions = 1;
        if(Objects.equals(granularity, "city")){
            for (int i = 0; i < e.getStartYears().length; i++) {
                if(Objects.equals(e.getCities()[i], location)){
                    countries.add(e.getCountries()[i]);
                    cities.add(e.getCities()[i]);
                    startYears.add(e.getStartYears()[i]);
                    e.setNumExhibitions(numExhibitions++);
                }
            }
        } else {
            for (int i = 0; i < e.getStartYears().length; i++) {
                if(Objects.equals(e.getCountries()[i], location)){
                    countries.add(e.getCountries()[i]);
                    cities.add(e.getCities()[i]);
                    startYears.add(e.getStartYears()[i]);
                    e.setNumExhibitions(numExhibitions++);
                }
            }
        }
        e.setCountries(countries.toArray(String[]::new));
        e.setCities(cities.toArray(String[]::new));
        e.setStartYears(startYears.toArray(Integer[]::new));
        return e;
    }

    private ExhibitsWithYearly filterLinksYearly(ExhibitsWithYearly e, String location, String granularity){
        ArrayList<String> countries = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();
        int numExhibitions = 1;
        if(Objects.equals(granularity, "city")){
            for (int i = 0; i < e.getCities().length; i++) {
                if(Objects.equals(e.getCities()[i], location)){
                    countries.add(e.getCountries()[i]);
                    cities.add(e.getCities()[i]);
                    e.setNumExhibitions(numExhibitions++);
                }
            }
        } else {
            for (int i = 0; i < e.getCountries().length; i++) {
                if(Objects.equals(e.getCountries()[i], location)){
                    countries.add(e.getCountries()[i]);
                    cities.add(e.getCities()[i]);
                    e.setNumExhibitions(numExhibitions++);
                }
            }
        }
        e.setCountries(countries.toArray(String[]::new));
        e.setCities(cities.toArray(String[]::new));
        return e;
    }
}
