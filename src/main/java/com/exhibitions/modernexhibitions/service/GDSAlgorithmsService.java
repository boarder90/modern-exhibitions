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
     * @param year nullable parameter year, if the centralities of the yearly network should be returned
     * @return a list of centralities for the respective artist IDs
     * @throws NetworkTooLargeException if network size exceeds the maximum size of 30 nodes
     */
    List<CentralityDto> getDegreeCentrality(List<Integer> artistIds, Integer year) throws NetworkTooLargeException;

    /**
     *
     * @param artistIds IDs of artists (network)
     * @param year nullable parameter year, if the centralities of the yearly network should be returned
     * @return a list of centralitiies for the respective artist IDs
     * @throws NetworkTooLargeException if network size exceeds the maximum size of 30 nodes
     */
    List<CentralityDto> getDegreeCentralityWeighted(List<Integer> artistIds, Integer year) throws NetworkTooLargeException;

}
