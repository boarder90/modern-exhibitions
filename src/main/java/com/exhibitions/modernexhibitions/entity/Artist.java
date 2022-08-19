package com.exhibitions.modernexhibitions.entity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

/**
 * Artist entity
 */
@ToString
@Node("Artist")
@Getter
public class Artist {

    @Id
    private Integer id;
    private String name;
    private String nationality;
    private String sex;
    private String occupation;

    @Relationship(type="EXHIBITS_WITH_YEARLY", direction = Relationship.Direction.OUTGOING)
    private List<ExhibitsWithYearly> coArtistsOutgoing;

    @Relationship(type="EXHIBITS_WITH_YEARLY", direction = Relationship.Direction.INCOMING)
    private List<ExhibitsWithYearly> coArtistsIncoming;

    @Relationship(type="EXHIBITS_WITH_TOTAL", direction = Relationship.Direction.OUTGOING)
    private List<ExhibitsWithTotal> coArtistsTotalOutgoing;

    @Relationship(type="EXHIBITS_WITH_TOTAL", direction = Relationship.Direction.INCOMING)
    private List<ExhibitsWithTotal> coArtistsTotalIncoming;
}
