package com.exhibitions.modernexhibitions.entity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

/**
 * Exhibition entity
 */
@ToString
@Node("Exhibition")
@Getter
public class Exhibition {

    @Id
    private Integer id;
    private String title;
    private String startDate;
    private String endDate;
    private int startYear;
    private int endYear;
    private int numArtists;
    private int numCatalogueEntries;

    @Relationship(type="TAKES_PLACE", direction = Relationship.Direction.OUTGOING)
    List<Location> locations;
}
