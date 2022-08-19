package com.exhibitions.modernexhibitions.service.impl;

import com.exhibitions.modernexhibitions.dto.FeatureCollectionDto;
import com.exhibitions.modernexhibitions.dto.FeatureDto;
import com.exhibitions.modernexhibitions.dto.LocationsOfNetworkDto;
import com.exhibitions.modernexhibitions.exception.NetworkTooLargeException;
import com.exhibitions.modernexhibitions.repository.NonDomainRepository;
import com.exhibitions.modernexhibitions.service.NonDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class NonDomainServiceImpl implements NonDomainService {

    private final NonDomainRepository nonDomainRepository;

    @Autowired
    public NonDomainServiceImpl(NonDomainRepository nonDomainRepository) {
        this.nonDomainRepository = nonDomainRepository;
    }

    @Override
    public FeatureCollectionDto getExhibitionLocationsAsGeoJSON(List<Integer> artistIds) {
        return this.nonDomainRepository.getExhibitionLocationsAsGeoJSON( artistIds);
    }

    @Override
    public FeatureCollectionDto getExhibitionLocationsYearlyAsGeoJSON(List<Integer> artistIds) {

        return nonDomainRepository.getExhibitionLocationsYearlyAsGeoJSON(artistIds);
    }

    @Override
    public LocationsOfNetworkDto getLocationsOfNetwork(List<Integer> artistIds)  throws NetworkTooLargeException {
        if(artistIds!= null && artistIds.size()>30){
            throw new NetworkTooLargeException("Currently only 30 distinctive ids are supported.");
        }
        return nonDomainRepository.getLocationsOfNetwork(artistIds);
    }
}
