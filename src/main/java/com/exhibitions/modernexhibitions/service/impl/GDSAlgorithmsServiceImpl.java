package com.exhibitions.modernexhibitions.service.impl;

import com.exhibitions.modernexhibitions.dto.CentralityDto;
import com.exhibitions.modernexhibitions.repository.GDSAlgorithmsRepository;
import com.exhibitions.modernexhibitions.service.GDSAlgorithmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GDSAlgorithmsServiceImpl implements GDSAlgorithmsService {

    private final GDSAlgorithmsRepository gdsAlgorithmsRepository;

    @Autowired
    public GDSAlgorithmsServiceImpl(GDSAlgorithmsRepository gdsAlgorithmsRepository){
        this.gdsAlgorithmsRepository = gdsAlgorithmsRepository;
    }


    @Override
    public List<CentralityDto> getDegreeCentrality(List<Integer> artistIds) {
        return this.gdsAlgorithmsRepository.getDegreeCentrality(artistIds);
    }

    @Override
    public List<CentralityDto> getDegreeCentralityWeighted(List<Integer> artistIds) {
        return this.gdsAlgorithmsRepository.getDegreeCentralityWeighted(artistIds);
    }
}
