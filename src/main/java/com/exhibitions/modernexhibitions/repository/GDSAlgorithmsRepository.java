package com.exhibitions.modernexhibitions.repository;

import com.exhibitions.modernexhibitions.dto.CentralityDto;
import java.util.List;

public interface GDSAlgorithmsRepository {

    /**
     *
     * @param artistIds list of artist IDs (nodes)
     * @return Returns the degree centrality for each artist (ID)
     */
    List<CentralityDto> getDegreeCentrality(List<Integer> artistIds);

    /**
     *
     * @param artistIds list of artist IDs (nodes)
     * @return Returns the weighted degree centrality for each artist (ID)
     */
    List<CentralityDto> getDegreeCentralityWeighted(List<Integer> artistIds);

    /**
     * Drops the in-memory graph of Neo4j GDS
     */
    void dropGraph();

    /**
     * Creates an in-memory graph for Neo4j GDS - using Cypher projections and the yearly network
     *
     * @param artistIds list of artist IDs (nodes)
     * @param year year of the network
     */
    void createCypherGraphProjectionYearly(List<Integer> artistIds, Integer year);

    /**
     * Creates an in-memory graph for Neo4j GDS - using Cypher projections and the total network
     *
     * @param artistIds list of artist IDs (nodes)
     */
    void createCypherGraphProjection(List<Integer> artistIds);

}
