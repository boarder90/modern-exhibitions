package com.exhibitions.modernexhibitions.service;

import com.exhibitions.modernexhibitions.entity.Exhibition;
import com.exhibitions.modernexhibitions.exception.InvalidCoordinatesException;
import com.exhibitions.modernexhibitions.exception.NetworkTooLargeException;

import java.util.List;

/**
 * Service layer for exhibitions
 */
public interface ExhibitionService {

    /**
     *
     * @return Returns all Exhibitions
     */
    List<Exhibition> findAll();

    /**
     *
     * @param coordinates latitude and longitude of the exhibitions (optional filter)
     * @param artistIds IDs of artists (optional filter)
     * @param year year of the exhibitions (optional filter)
     * @return Returns a list of exhibitions
     * @throws NetworkTooLargeException if network size exceeds the maximum size of 30 nodes
     * @throws InvalidCoordinatesException if coordinates format is invalid
     */
    List<Exhibition> findExhibitionsFiltered (List<Double> coordinates, List<Integer> artistIds, Integer year) throws NetworkTooLargeException, InvalidCoordinatesException;
}
