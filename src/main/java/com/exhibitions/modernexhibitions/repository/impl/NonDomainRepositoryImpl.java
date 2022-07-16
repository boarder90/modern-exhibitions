package com.exhibitions.modernexhibitions.repository.impl;

import com.exhibitions.modernexhibitions.dto.FeatureCollectionDto;
import com.exhibitions.modernexhibitions.dto.FeatureDto;
import com.exhibitions.modernexhibitions.dto.LinkDto;
import com.exhibitions.modernexhibitions.entity.Artist;
import com.exhibitions.modernexhibitions.repository.NonDomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Repository
public class NonDomainRepositoryImpl implements NonDomainRepository {

    private final Neo4jClient neo4jClient;

    @Autowired
    NonDomainRepositoryImpl(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    public List<LinkDto> getEgoNetworkRelationships(Integer id, Integer numExhibitions) {
      /*  Collection<LinkDto> result=  this.neo4jClient
                .query("CALL\n" +
                        "{MATCH (a1:Artist)-[t:EXHIBITS_WITH]-(a2:Artist) WHERE a1.id = $id and t.numExhibitions > 0 \n" +
                        "RETURN a1.id as startId, sum(t.numExhibitions) AS numExhibitions,REDUCE(s = [],\n" +
                        "cities IN COLLECT(t.cities) | s + cities) as cities,REDUCE(s = [], countries IN COLLECT(t.countries) | s + countries) as countries, REDUCE(s = [], startYear IN COLLECT(t.startYear) | s + startYear) as startYears, REDUCE(s = [], endYear IN COLLECT(t.endYear) | s + endYear) as endYears, a2.id as endId\n" +
                        "} \n" +
                        "WITH startId, numExhibitions, startYears, endYears, cities, countries, endId\n" +
                        "WHERE numExhibitions>=$numExhibitions\n" +
                        "RETURN startId, numExhibitions, startYears, endYears, cities, countries, endId\n" +
                        "ORDER BY numExhibitions DESC;").bind(id).to("id").bind(numExhibitions).to("numExhibitions").fetchAs(LinkDto.class).mappedBy((typeSystem, record) ->
                        new LinkDto(record.get("startId").asInt(),record.get("endId").asInt(), Arrays.stream(record.get("startYears").asList().toArray())
                                .map(o -> (Long) o)
                                .toArray(Long[]::new),
                                Arrays.stream(record.get("endYears").asList().toArray())
                                .map(o -> (Long) o)
                                .toArray(Long[]::new),
                                record.get("numExhibitions").asInt(),
                                Arrays.stream(record.get("cities").asList().toArray())
                                .map(o -> (String) o)
                                .toArray(String[]::new),
                                Arrays.stream(record.get("countries").asList().toArray())
                                        .map(o -> (String) o)
                                        .toArray(String[]::new))).all();
         return result.stream().toList();*/
        return null;
    }

    public List<LinkDto> getEgoNetworkOneHalfRelationships(Integer id, Integer numExhibitions) {
       /* Collection<LinkDto> result=  this.neo4jClient
                .query("CALL {\n" +
                        "    MATCH (a1{id:$id})-[e:EXHIBITS_WITH]-(a2) \n" +
                        "    RETURN a1.id as startId, sum(e.numExhibitions) AS numExhibitions,REDUCE(s = [], cities IN COLLECT(e.cities) | s +      cities) as cities,REDUCE(s = [], countries IN COLLECT(e.countries) | s + countries) as countries, REDUCE(s = [], startYear IN COLLECT(e.startYear) | s + startYear) as startYears,REDUCE(s = [], endYear IN COLLECT(e.endYear) | s + endYear) as endYears, a2.id as endId\n" +
                        "    UNION\n" +
                        "    MATCH (a1{id:$id})-[e:EXHIBITS_WITH]-(a2) WITH collect(a2.id) as coartists\n" +
                        "    MATCH (a3:Artist)-[f:EXHIBITS_WITH]-(a4:Artist) where a3.id in coartists and a4.id in coartists and id(a3) < id(a4) and f.numExhibitions>=0\n" +
                        "    RETURN a3.id as startId, sum(f.numExhibitions) AS numExhibitions,REDUCE(s = [], cities IN COLLECT(f.cities) | s +      cities) as cities,REDUCE(s = [], countries IN COLLECT(f.countries) | s + countries) as countries, REDUCE(s = [], startYear IN COLLECT(f.startYear) | s + startYear) as startYears,REDUCE(s = [], endYear IN COLLECT(f.endYear) | s + endYear) as endYears, a4.id as endId\n" +
                        "}\n" +
                        "WITH startId, numExhibitions, startYears, endYears, cities, countries, endId\n" +
                        "WHERE numExhibitions>=$numExhibitions\n" +
                        "RETURN startId, numExhibitions, startYears, endYears, cities, countries, endId\n" +
                        "ORDER BY numExhibitions DESC").bind(id).to("id").bind(numExhibitions).to("numExhibitions").fetchAs(LinkDto.class).mappedBy((typeSystem, record) ->
                        new LinkDto(record.get("startId").asInt(),record.get("endId").asInt(), Arrays.stream(record.get("startYears").asList().toArray())
                                .map(o -> (Long) o)
                                .toArray(Long[]::new),
                                Arrays.stream(record.get("endYears").asList().toArray())
                                        .map(o -> (Long) o)
                                        .toArray(Long[]::new),
                                record.get("numExhibitions").asInt(),
                                Arrays.stream(record.get("cities").asList().toArray())
                                        .map(o -> (String) o)
                                        .toArray(String[]::new),
                                Arrays.stream(record.get("countries").asList().toArray())
                                        .map(o -> (String) o)
                                        .toArray(String[]::new))).all();
        return result.stream().toList();*/
        return null;
    }

    public List<LinkDto> getNetworkRelationshipsByIds(List<Integer> ids, Integer numExhibitions) {
        /*Collection<LinkDto> result=  this.neo4jClient
                .query("CALL\n" +
                        "{MATCH (a1:Artist)-[t:EXHIBITS_WITH]-(a2:Artist) WHERE a1.id in $ids and a2.id in $ids and id(a1)<id(a2)\n" +
                        "RETURN a1.id as startId, sum(t.numExhibitions) AS numExhibitions,REDUCE(s = [],\n" +
                        "cities IN COLLECT(t.cities) | s + cities) as cities,REDUCE(s = [], countries IN COLLECT(t.countries) | s + countries) as countries, REDUCE(s = [], startYear IN COLLECT(t.startYear) | s + startYear) as startYears, REDUCE(s = [], endYear IN COLLECT(t.endYear) | s + endYear) as endYears, a2.id as endId\n" +
                        "} \n" +
                        "WITH startId, numExhibitions, startYears, endYears, cities, countries, endId\n" +
                        "WHERE numExhibitions>$numExhibitions\n" +
                        "RETURN startId, numExhibitions, startYears, endYears, cities, countries, endId\n" +
                        "ORDER BY numExhibitions DESC;").bind(ids).to("ids").bind(numExhibitions).to("numExhibitions").fetchAs(LinkDto.class).mappedBy((typeSystem, record) ->
                        new LinkDto(record.get("startId").asInt(),record.get("endId").asInt(), Arrays.stream(record.get("startYears").asList().toArray())
                                .map(o -> (Long) o)
                                .toArray(Long[]::new),
                                Arrays.stream(record.get("endYears").asList().toArray())
                                        .map(o -> (Long) o)
                                        .toArray(Long[]::new),
                                record.get("numExhibitions").asInt(),
                                Arrays.stream(record.get("cities").asList().toArray())
                                        .map(o -> (String) o)
                                        .toArray(String[]::new),
                                Arrays.stream(record.get("countries").asList().toArray())
                                        .map(o -> (String) o)
                                        .toArray(String[]::new))).all();
        return result.stream().toList();*/
                return null;
    }

    @Override
    public FeatureCollectionDto getExhibitionLocationsAsGeoJSON(List<Integer> artistIds) {
        Collection<FeatureDto> result = this.neo4jClient.query("""
                        CALL apoc.do.when($artistIds IS NOT NULL,
                          'match (a1:Artist)-[:EXHIBITS_AT]->(e:Exhibition)<-[:EXHIBITS_AT]-(a2:Artist),(e:Exhibition)-[:TAKES_PLACE]->(l:Location) where a1.id in $artistIds and a2.id in $artistIds and l.longitude IS NOT NULL return l.longitude as longitude, l.latitude as latitude, count(distinct e) as numExhibitions',
                          'match(e:Exhibition)-[:TAKES_PLACE]->(l:Location) where l.longitude IS NOT NULL return l.longitude as longitude, l.latitude as latitude, count(distinct e) as numExhibitions',
                          {artistIds:$artistIds}
                        )
                        YIELD value
                        RETURN value.longitude AS longitude, value.latitude AS latitude, value.numExhibitions as numExhibitions ;
                        """).
                bind(artistIds).to("artistIds").fetchAs(FeatureDto.class).mappedBy((typeSystem, record) ->
                        new FeatureDto(new FeatureDto.PropertyDto(record.get("numExhibitions").asInt(),null), new FeatureDto.GeometryDto(Arrays.stream(new Double[]{ record.get("longitude").asDouble(),record.get("latitude").asDouble()}).toList()))).all();
        return new FeatureCollectionDto(result.stream().toList());
    }

    @Override
    public FeatureCollectionDto getExhibitionLocationsYearlyAsGeoJSON(List<Integer> artistIds) {
        Collection<FeatureDto> result = this.neo4jClient.query("""
                        CALL apoc.do.when($artistIds IS NOT NULL,
                          'match (a1:Artist)-[:EXHIBITS_AT]->(e:Exhibition)<-[:EXHIBITS_AT]-(a2:Artist),(e:Exhibition)-[:TAKES_PLACE]->(l:Location) where a1.id in $artistIds and a2.id in $artistIds and l.longitude IS NOT NULL return l.longitude as longitude, l.latitude as latitude, e.startYear as year, count(distinct e) as numExhibitions',
                          'match(e:Exhibition)-[:TAKES_PLACE]->(l:Location) where l.longitude IS NOT NULL return l.longitude as longitude, l.latitude as latitude, e.startYear as year, count(distinct e) as numExhibitions',
                          {artistIds: $artistIds}
                        )
                        YIELD value
                        RETURN value.longitude AS longitude, value.latitude AS latitude, value.year as year, value.numExhibitions as numExhibitions ;""")
                .bind(artistIds).to("artistIds").fetchAs(FeatureDto.class).mappedBy((typeSystem, record) ->
                        new FeatureDto(new FeatureDto.PropertyDto(record.get("numExhibitions").asInt(), record.get("year").asInt()), new FeatureDto.GeometryDto(Arrays.stream(new Double[]{record.get("longitude").asDouble(), record.get("latitude").asDouble()}).toList()))).all();
        return new FeatureCollectionDto(result.stream().toList());
    }


    @Override
    public List<FeatureDto> getExhibitionLocationsByEndYear(List<Integer> artistIds) {
        Collection<FeatureDto> result = this.neo4jClient.query("""
                        CALL apoc.do.when($artistIds IS NOT NULL,
                          'match (a1:Artist)-[:EXHIBITS_AT]->(e:Exhibition)<-[:EXHIBITS_AT]-(a2:Artist),(e:Exhibition)-[:TAKES_PLACE]->(l:Location) where a1.id in $artistIds and a2.id in $artistIds and l.longitude IS NOT NULL and e.endYear IS NOT NULL return l.longitude as longitude, l.latitude as latitude, e.endYear as year, count(distinct e) as numExhibitions',
                          'match(e:Exhibition)-[:TAKES_PLACE]->(l:Location) where l.longitude IS NOT NULL and e.endYear IS NOT NULL return l.longitude as longitude, l.latitude as latitude, e.endYear as year, count(distinct e) as numExhibitions',
                          {artistIds: $artistIds}
                        )
                        YIELD value
                        RETURN value.longitude AS longitude, value.latitude AS latitude, value.year as year, value.numExhibitions as numExhibitions ;""")
                .bind(artistIds).to("artistIds").fetchAs(FeatureDto.class).mappedBy((typeSystem, record) ->
                        new FeatureDto(new FeatureDto.PropertyDto(record.get("numExhibitions").asInt(), record.get("year").asInt()), new FeatureDto.GeometryDto(Arrays.stream(new Double[]{record.get("longitude").asDouble(),record.get("latitude").asDouble()}).toList()))).all();
        return result.stream().toList();
    }

   /* @Query("CALL {\n" +
            "    MATCH (a1{id:2})-[e:EXHIBITS_WITH]-(a2) WHERE e.numExhibitions >= 0\n" +
            "    RETURN a1.id as startId, sum(e.numExhibitions) AS numExhibitions,REDUCE(s = [], cities IN COLLECT(e.cities) | s +      cities) as cities,REDUCE(s = [], countries IN COLLECT(e.countries) | s + countries) as countries, REDUCE(s = [], startYear IN COLLECT(e.startYear) | s + startYear) as startYears,REDUCE(s = [], endYear IN COLLECT(e.endYear) | s + endYear) as endYears, a2.id as endId\n" +
            "    UNION\n" +
            "    MATCH (a1{id:2})-[e:EXHIBITS_WITH]-(a2) WHERE e.numExhibitions >= 0 WITH collect(a2.id) as coartists\n" +
            "    MATCH (a3:Artist)-[f:EXHIBITS_WITH]-(a4:Artist) where a3.id in coartists and a4.id in coartists and id(a3) < id(a4) and f.numExhibitions>=0\n" +
            "    RETURN a3.id as startId, sum(f.numExhibitions) AS numExhibitions,REDUCE(s = [], cities IN COLLECT(f.cities) | s +      cities) as cities,REDUCE(s = [], countries IN COLLECT(f.countries) | s + countries) as countries, REDUCE(s = [], startYear IN COLLECT(f.startYear) | s + startYear) as startYears,REDUCE(s = [], endYear IN COLLECT(f.endYear) | s + endYear) as endYears, a4.id as endId\n" +
            "}\n" +
            "WITH startId, numExhibitions, startYears, endYears, cities, countries, endId\n" +
            "WHERE numExhibitions>3\n" +
            "RETURN startId, numExhibitions, startYears, endYears, cities, countries, endId\n" +
            "ORDER BY numExhibitions DESC")*/
}
