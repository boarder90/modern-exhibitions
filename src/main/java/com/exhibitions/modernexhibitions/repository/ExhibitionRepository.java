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

    List<Exhibition> findAll();

    @Query("match(a1:Artist)-[r:EXHIBITS_AT]->(e:Exhibition) WHERE a1.id=$id return e")
    List<Exhibition> findExhibitionsOfArtist(Integer id);

    @Query("CALL apoc.do.case([$artistIds IS NOT NULL," +
            " \"MATCH (a:Artist) -[:EXHIBITS_AT]->(e:Exhibition) " +
            "WHERE ($year IS NULL OR (e.startYear = $year OR e.endYear= $year))" +
            " AND a.id IN $artists RETURN e\"], \"Match (e:Exhibition) WHERE ($year" +
            " IS NULL OR (e.startYear = $year OR e.endYear= $year)) RETURN " +
            "e\",{artists:$artistIds,year:$year}) YIELD value RETURN value.e")
    List<Exhibition> findExhibitionsFiltered(List<Integer> artistIds, Integer year);
}
