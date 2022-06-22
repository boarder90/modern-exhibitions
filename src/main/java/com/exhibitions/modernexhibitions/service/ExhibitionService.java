package com.exhibitions.modernexhibitions.service;

import com.exhibitions.modernexhibitions.entity.Exhibition;

import java.util.List;

public interface ExhibitionService {

    List<Exhibition> findAll();

    List<Exhibition> findExhibitionsFiltered (List<Double> coordinates, List<Integer> artistIds, Integer year);
}
