package com.exhibitions.modernexhibitions.entity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@ToString
@Node("Artist")
public class Artist {

    @Id
    @Getter
    private Integer id;

    @Getter
    private String name;

    @Getter
    private String nationality;

    @Getter
    private String sex;

    @Getter
    private String occupation;

    @Getter
    @Relationship(type="EXHIBITS_WITH_YEARLY", direction = Relationship.Direction.OUTGOING)
    private List<ExhibitsWithYearly> coArtistsOutgoing;

    @Getter
    @Relationship(type="EXHIBITS_WITH_YEARLY", direction = Relationship.Direction.INCOMING)
    private List<ExhibitsWithYearly> coArtistsIncoming;

    @Getter
    @Relationship(type="EXHIBITS_WITH_TOTAL", direction = Relationship.Direction.OUTGOING)
    private List<ExhibitsWithTotal> coArtistsTotalOutgoing;

    @Getter
    @Relationship(type="EXHIBITS_WITH_TOTAL", direction = Relationship.Direction.INCOMING)
    private List<ExhibitsWithTotal> coArtistsTotalIncoming;
}
