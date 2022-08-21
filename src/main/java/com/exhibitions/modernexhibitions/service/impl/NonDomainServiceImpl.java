package com.exhibitions.modernexhibitions.service.impl;

import com.exhibitions.modernexhibitions.dto.FeatureCollectionDto;
import com.exhibitions.modernexhibitions.dto.LocationsOfNetworkDto;
import com.exhibitions.modernexhibitions.exception.NetworkTooLargeException;
import com.exhibitions.modernexhibitions.repository.NonDomainRepository;
import com.exhibitions.modernexhibitions.service.NonDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class NonDomainServiceImpl implements NonDomainService {

    private final NonDomainRepository nonDomainRepository;
    private final Logger logger = LoggerFactory.getLogger(NonDomainServiceImpl.class);

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
            logger.error("Network size exceeds 30! Current size: " + artistIds.size() +".");
            throw new NetworkTooLargeException("Currently only 30 distinctive ids are supported.");
        }
        return nonDomainRepository.getLocationsOfNetwork(artistIds);
    }
}
