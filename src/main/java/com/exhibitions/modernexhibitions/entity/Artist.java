package com.exhibitions.modernexhibitions.entity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

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
    private String occupation;
}
