package com.exhibitions.modernexhibitions.service;

import com.exhibitions.modernexhibitions.dto.CentralityDto;
import com.exhibitions.modernexhibitions.exception.NetworkTooLargeException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for all the algorithms used in the GDS library.
 */
@Transactional
public interface GDSAlgorithmsService {

    /**
     *
     * @param artistIds IDs of artists
     * @param year nullable parameter year, if the centralities of the yearly network
     * @return a list of centralities for the respective artist IDs
     */
    List<CentralityDto> getDegreeCentrality(List<Integer> artistIds, Integer year) throws NetworkTooLargeException;

    /**
     *
     * @param artistIds
     * @param year
     * @return
     */
    List<CentralityDto> getDegreeCentralityWeighted(List<Integer> artistIds, Integer year) throws NetworkTooLargeException;

}
