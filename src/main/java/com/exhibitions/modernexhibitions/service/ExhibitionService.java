package com.exhibitions.modernexhibitions.service;

import com.exhibitions.modernexhibitions.entity.Exhibition;
import com.exhibitions.modernexhibitions.exception.InvalidCoordinatesException;
import com.exhibitions.modernexhibitions.exception.NetworkTooLargeException;

import java.util.List;

public interface ExhibitionService {

    List<Exhibition> findAll();

    List<Exhibition> findExhibitionsFiltered (List<Double> coordinates, List<Integer> artistIds, Integer year) throws NetworkTooLargeException, InvalidCoordinatesException;
}
