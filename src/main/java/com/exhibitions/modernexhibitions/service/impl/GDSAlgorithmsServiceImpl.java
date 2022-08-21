package com.exhibitions.modernexhibitions.service.impl;

import com.exhibitions.modernexhibitions.dto.CentralityDto;
import com.exhibitions.modernexhibitions.exception.NetworkTooLargeException;
import com.exhibitions.modernexhibitions.repository.GDSAlgorithmsRepository;
import com.exhibitions.modernexhibitions.service.GDSAlgorithmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GDSAlgorithmsServiceImpl implements GDSAlgorithmsService {

    private final GDSAlgorithmsRepository gdsAlgorithmsRepository;
    private final Logger logger = LoggerFactory.getLogger(GDSAlgorithmsServiceImpl.class);

    @Autowired
    public GDSAlgorithmsServiceImpl(GDSAlgorithmsRepository gdsAlgorithmsRepository){
        this.gdsAlgorithmsRepository = gdsAlgorithmsRepository;
    }

    @Override
    public List<CentralityDto> getDegreeCentrality(List<Integer> artistIds, Integer year) throws NetworkTooLargeException{
        if(artistIds!= null && artistIds.size()>30){
            logger.error("Network size exceeds 30! Current size: " + artistIds.size() +".");
            throw new NetworkTooLargeException("Currently only 30 distinctive ids are supported.");
        }
        synchronized (this){
            if(year == null){
                this.gdsAlgorithmsRepository.dropGraph();
                this.gdsAlgorithmsRepository.createCypherGraphProjection(artistIds);
                return this.gdsAlgorithmsRepository.getDegreeCentrality(artistIds);
            } else {
                this.gdsAlgorithmsRepository.dropGraph();
                this.gdsAlgorithmsRepository.createCypherGraphProjectionYearly(artistIds, year);
                return this.gdsAlgorithmsRepository.getDegreeCentrality(artistIds);
            }
        }
    }

    @Override
    public List<CentralityDto> getDegreeCentralityWeighted(List<Integer> artistIds, Integer year) throws NetworkTooLargeException{
        if(artistIds!= null && artistIds.size()>30){
            logger.error("Network size exceeds 30! Current size: " + artistIds.size() +".");
            throw new NetworkTooLargeException("Currently only 30 distinctive ids are supported.");
        }
        synchronized (this) {
            if (year == null) {
                this.gdsAlgorithmsRepository.dropGraph();
                this.gdsAlgorithmsRepository.createCypherGraphProjection(artistIds);
                return this.gdsAlgorithmsRepository.getDegreeCentralityWeighted(artistIds);
            } else {
                this.gdsAlgorithmsRepository.dropGraph();
                this.gdsAlgorithmsRepository.createCypherGraphProjectionYearly(artistIds, year);
                return this.gdsAlgorithmsRepository.getDegreeCentralityWeighted(artistIds);
            }
        }
    }
}
