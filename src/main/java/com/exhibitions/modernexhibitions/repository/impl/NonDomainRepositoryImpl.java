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
}
