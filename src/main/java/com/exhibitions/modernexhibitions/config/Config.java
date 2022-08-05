package com.exhibitions.modernexhibitions.config;

import org.modelmapper.ModelMapper;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.Neo4jClient;

/**
 * Create ModelMapper and Neo4jClient Beans
 */
@Configuration
public class Config {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Neo4jClient neo4jClient() {
        Driver driver = GraphDatabase
                .driver("neo4j://localhost:7687", AuthTokens.basic("neo4j", "secret"));
        return Neo4jClient.create(driver);}
}
