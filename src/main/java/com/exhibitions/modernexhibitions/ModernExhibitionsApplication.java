package com.exhibitions.modernexhibitions;

import com.exhibitions.modernexhibitions.entity.Exhibition;
import com.exhibitions.modernexhibitions.repository.ExhibitionRepository;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.core.Neo4jClient;

import java.util.*;

@SpringBootApplication
public class ModernExhibitionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModernExhibitionsApplication.class, args);

	}
}
