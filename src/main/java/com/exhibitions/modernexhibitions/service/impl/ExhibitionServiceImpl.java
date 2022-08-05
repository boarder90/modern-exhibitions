package com.exhibitions.modernexhibitions.service.impl;

import com.exhibitions.modernexhibitions.entity.Exhibition;
import com.exhibitions.modernexhibitions.exception.InvalidCoordinatesException;
import com.exhibitions.modernexhibitions.exception.NetworkTooLargeException;
import com.exhibitions.modernexhibitions.repository.ExhibitionRepository;
import com.exhibitions.modernexhibitions.service.ExhibitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ExhibitionServiceImpl implements ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;

    @Autowired
    public ExhibitionServiceImpl(ExhibitionRepository e){
        this.exhibitionRepository = e;
    }

    public List<Exhibition> findAll (){
        return this.exhibitionRepository.findAll();
    }

    public List<Exhibition> findExhibitionsFiltered (List<Double> coordinates, List<Integer> artistIds, Integer year) throws InvalidCoordinatesException, NetworkTooLargeException {
        if(artistIds!= null && artistIds.size()>30){
            throw new NetworkTooLargeException("Currently only 30 distinctive ids are supported.");
        }
        if(coordinates == null){
            return this.exhibitionRepository.findExhibitionsFiltered(null, null, artistIds, year);
        }
        if(coordinates.get(1)< -90 || coordinates.get(1)>90||coordinates.get(0)<-180 || coordinates.get(0)>180){
            throw new InvalidCoordinatesException("Invalid coordinates");
        }

            return this.exhibitionRepository.findExhibitionsFiltered(coordinates.get(1), coordinates.get(0), artistIds, year);

    }


}
