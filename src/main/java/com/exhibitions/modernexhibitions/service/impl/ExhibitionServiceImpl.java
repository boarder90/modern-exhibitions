package com.exhibitions.modernexhibitions.service.impl;

import com.exhibitions.modernexhibitions.entity.Exhibition;
import com.exhibitions.modernexhibitions.repository.ExhibitionRepository;
import com.exhibitions.modernexhibitions.service.ExhibitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExhibitionServiceImpl implements ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;

    @Autowired
    public ExhibitionServiceImpl(ExhibitionRepository e){
        this.exhibitionRepository = e;
    }

    public List<Exhibition> findAll (){
        System.out.println(exhibitionRepository.findAll());
        return this.exhibitionRepository.findAll();
    }

    public List<Exhibition> findExhibitionsFiltered (List<Double> coordinates, List<Integer> artistIds, Integer year){
        //System.out.println(coordinates.toString());
        //System.out.println(coordinates.toString());
        System.out.println(artistIds == null);
        if(coordinates == null){
            return this.exhibitionRepository.findExhibitionsFiltered(null, null, artistIds, year);
        } else {
            return this.exhibitionRepository.findExhibitionsFiltered(coordinates.get(1), coordinates.get(0), artistIds, year);
        }
    }


}
