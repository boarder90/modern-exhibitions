package com.exhibitions.modernexhibitions.repository;

import com.exhibitions.modernexhibitions.entity.Exhibition;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExhibitionRepository extends Neo4jRepository<Exhibition, Integer> {

    Optional<Exhibition> findById(Integer aInteger);

    @Query("match (e:Exhibition)-[l:TAKES_PLACE]->(s:Location) RETURN e, collect(l), collect(s)")
    List<Exhibition> findAll();

    @Query("match(a1:Artist)-[r:EXHIBITS_AT]->(e:Exhibition) WHERE a1.id=$id return e")
    List<Exhibition> findExhibitionsOfArtist(Integer id);

    @Query("CALL apoc.do.case([$artistIds IS NOT NULL AND $latitude IS NULL, \n" +
            "             \"MATCH (a:Artist) -[:EXHIBITS_AT]->(e:Exhibition) WHERE ($year IS NULL OR" +
            " (e.startYear = $year OR e.endYear= $year)) AND a.id IN $artists RETURN e\",\n" +
            "             $artistIds IS NOT NULL AND $latitude IS NOT NULL,\n" +
            "              \"MATCH (a:Artist) -[:EXHIBITS_AT]->(e:Exhibition)-[t:TAKES_PLACE]->(l:Location) " +
            "WHERE ($year IS NULL OR (e.startYear = $year OR e.endYear= $year)) AND a.id IN $artists " +
            "AND l.longitude=$longitude and l.latitude = $latitude RETURN e, collect(t) as t, collect(l) as l\", $artistIds IS NULL AND $latitude IS NOT NULL, \n" +
            "              \"MATCH(e:Exhibition)-[t:TAKES_PLACE]->(l:Location) WHERE " +
            "($year IS NULL OR (e.startYear = $year OR e.endYear= $year)) AND l.longitude=$longitude and l.latitude = $latitude RETURN e, collect(t) as t, collect(l) as l\"]," +
            " \"Match (e:Exhibition) WHERE $year IS NULL OR (e.startYear = $year OR e.endYear= $year) " +
            "RETURN e\",{artists:$artistIds,year:$year, longitude: $longitude, latitude: $latitude}) YIELD value RETURN value.e as exhibition, value.t, value.l")
    List<Exhibition> findExhibitionsFiltered(Double latitude, Double longitude, List<Integer> artistIds, Integer year);
}
