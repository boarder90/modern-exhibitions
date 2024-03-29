package com.exhibitions.modernexhibitions.repository;

import com.exhibitions.modernexhibitions.entity.Exhibition;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Exhibition repository layer
 */
@Repository
public interface ExhibitionRepository extends Neo4jRepository<Exhibition, Integer> {

    Optional<Exhibition> findById(Integer aInteger);

    /**
     *
     * @return Returns all Exhibitions
     */
    @Query("match (e:Exhibition)-[l:TAKES_PLACE]->(s:Location) RETURN e, collect(l), collect(s)")
    List<Exhibition> findAll();

    /**
     *
     * @param latitude latitude provided as Double (optional)
     * @param longitude longitude provided as Double (optional)
     * @param artistIds artistIds provided as a list of Integers (optional)
     * @param year exhibition year (optional)
     * @return Returns a list of exhibitions
     */
    @Query("""
            CALL apoc.do.case([$artistIds IS NOT NULL AND $latitude IS NULL,\s
                         "match (a1:Artist)-[:EXHIBITS_AT]->(e:Exhibition)<-[:EXHIBITS_AT]-(a2:Artist) WHERE ($year IS NULL OR (e.startYear = $year OR e.endYear= $year)) AND a1.id IN $artists and a2.id IN $artists RETURN e",
                         $artistIds IS NOT NULL AND $latitude IS NOT NULL,
                          "match (a1:Artist)-[:EXHIBITS_AT]->(e:Exhibition)<-[:EXHIBITS_AT]-(a2:Artist),(e:Exhibition)-[t:TAKES_PLACE]->(l:Location) WHERE ($year IS NULL OR (e.startYear = $year OR e.endYear= $year)) AND a1.id IN $artists AND a2.id IN $artists AND l.longitude=$longitude and l.latitude = $latitude RETURN e, collect(t) as t, collect(l) as l", $artistIds IS NULL AND $latitude IS NOT NULL,\s
                          "MATCH(e:Exhibition)-[t:TAKES_PLACE]->(l:Location) WHERE ($year IS NULL OR (e.startYear = $year OR e.endYear= $year)) AND l.longitude=$longitude and l.latitude = $latitude RETURN e, collect(t) as t, collect(l) as l"], "Match (e:Exhibition) WHERE $year IS NULL OR (e.startYear = $year OR e.endYear= $year) RETURN e",{artists:$artistIds,year:$year, longitude: $longitude, latitude: $latitude}) YIELD value RETURN value.e as exhibition, value.t, value.l""")
    List<Exhibition> findExhibitionsFiltered(Double latitude, Double longitude, List<Integer> artistIds, Integer year);
}
