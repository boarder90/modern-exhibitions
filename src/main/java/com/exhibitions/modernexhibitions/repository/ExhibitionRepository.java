package com.exhibitions.modernexhibitions.repository;

import com.exhibitions.modernexhibitions.entity.Exhibition;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface ExhibitionRepository extends Neo4jRepository<Exhibition, Long> {

    Optional<Exhibition> findById(Long aLong);

    List<Exhibition> findAll();

    @Query("match(a1:Artist)-[r:EXHIBITS_AT]->(e:Exhibition) WHERE a1.id=$id return e")
    List<Exhibition> findExhibitionsOfArtist(Long id);
}
