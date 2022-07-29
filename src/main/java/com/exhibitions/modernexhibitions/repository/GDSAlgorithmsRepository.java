package com.exhibitions.modernexhibitions.repository;

import com.exhibitions.modernexhibitions.dto.CentralityDto;
import java.util.List;

public interface GDSAlgorithmsRepository {
    List<CentralityDto> getDegreeCentrality(List<Integer> artistIds);

    List<CentralityDto> getDegreeCentralityWeighted(List<Integer> artistIds);

    void dropGraph();

    void createCypherGraphProjectionYearly(List<Integer> artistIds, Integer year);

    void createCypherGraphProjection(List<Integer> artistIds);

}
