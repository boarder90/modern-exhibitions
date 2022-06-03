package com.exhibitions.modernexhibitions.entity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@ToString
@Node("Exhibition")
public class Exhibition {

    @Id
    private Long id;

    @Getter
    private String title;

    @Getter
    private String startDate;

    @Getter
    private String endDate;

    @Getter
    private int startYear;

    @Getter
    private int endYear;

    @Getter
    private int numArtists;

    @Getter
    private int numCatalogueEntries;

    @Getter
    @Relationship(type="TAKES_PLACE", direction = Relationship.Direction.OUTGOING)
    List<Location> locations;
}
