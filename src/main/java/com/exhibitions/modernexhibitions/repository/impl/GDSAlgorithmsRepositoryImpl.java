package com.exhibitions.modernexhibitions.repository.impl;

import com.exhibitions.modernexhibitions.dto.CentralityDto;
import com.exhibitions.modernexhibitions.repository.GDSAlgorithmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GDSAlgorithmsRepositoryImpl implements GDSAlgorithmsRepository {

    private final Neo4jClient neo4jClient;

    @Autowired
    GDSAlgorithmsRepositoryImpl(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    @Override
    public List<CentralityDto> getDegreeCentrality(List<Integer> artistIds) {
        return this.neo4jClient.query("""
                        CALL gds.degree.stream(
                           'artistNetwork',
                           { orientation: 'UNDIRECTED'}
                        )
                        YIELD nodeId, score
                        RETURN gds.util.asNode(nodeId).id AS id, score AS centrality
                        ORDER BY centrality DESC, id DESC""").fetchAs(CentralityDto.class).
                mappedBy(((typeSystem, record) -> new CentralityDto(record.get("id").asInt(),record.get("centrality").asDouble()))).all().stream().toList();
    }

    @Override
    public List<CentralityDto> getDegreeCentralityWeighted(List<Integer> artistIds) {
        return this.neo4jClient.query("""
                        CALL gds.degree.stream(
                           'artistNetwork',
                           { orientation: 'UNDIRECTED',relationshipWeightProperty: 'numberOfExhibitions' }
                        )
                        YIELD nodeId, score
                        RETURN gds.util.asNode(nodeId).id AS id, score AS centrality
                        ORDER BY centrality DESC, id DESC""").fetchAs(CentralityDto.class).
                mappedBy(((typeSystem, record) -> new CentralityDto(record.get("id").asInt(),record.get("centrality").asDouble()))).all().stream().toList();
    }

    @Override
    public void dropGraph() {
        this.neo4jClient.query("CALL gds.graph.drop('artistNetwork', false)").run();
    }

    @Override
    public void createCypherGraphProjection(List<Integer> artistIds) {
        this.neo4jClient.query("""
                CALL gds.graph.project.cypher(
                  'artistNetwork',
                  'MATCH (n:Artist) WHERE n.id in $artistIds RETURN id(n) AS id',
                  'MATCH (n:Artist)-[r:EXHIBITS_WITH_TOTAL]-(m:Artist) WHERE n.id in $artistIds and m.id in $artistIds and id(n)<id(m) RETURN id(n) AS source, id(m) AS target,r.numExhibitions AS numberOfExhibitions', {parameters: { artistIds: $artistIds} })
                YIELD
                  graphName AS graph""").bind(artistIds).to("artistIds").run();

    }

    @Override
    public void createCypherGraphProjectionYearly(List<Integer> artistIds, Integer year) {
        this.neo4jClient.query("""
                CALL gds.graph.project.cypher(
                  'artistNetwork',
                  'MATCH (n:Artist) WHERE n.id in $artistIds RETURN id(n) AS id',
                  'MATCH (n:Artist)-[r:EXHIBITS_WITH_YEARLY]-(m:Artist) WHERE n.id in $artistIds and m.id in $artistIds and id(n)<id(m) and r.startYear = $year RETURN id(n) AS source, id(m) AS target,r.numExhibitions AS numberOfExhibitions', {parameters: { artistIds: $artistIds, year: $year} })
                YIELD
                  graphName AS graph""").bind(artistIds).to("artistIds").bind(year).to("year").run();

    }

}
