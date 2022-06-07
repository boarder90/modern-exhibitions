package com.exhibitions.modernexhibitions.service.impl;

import com.exhibitions.modernexhibitions.entity.Exhibition;
import com.exhibitions.modernexhibitions.repository.ExhibitionRepository;
import com.exhibitions.modernexhibitions.service.ExhibitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ExhibitionServiceImpl implements ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;

    @Autowired
    public ExhibitionServiceImpl(ExhibitionRepository e){
        this.exhibitionRepository = e;
    }

    public List<Exhibition> findAll (){
        return this.exhibitionRepository.findAll();
    }

    public List<Exhibition> findExhibitionsFiltered (List<Integer> artistIds, Integer year){
        System.out.println(artistIds == null);
        return this.exhibitionRepository.findExhibitionsFiltered(artistIds, year);
    }


}
