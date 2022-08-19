package com.exhibitions.modernexhibitions.entity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * Location entity
 */
@ToString
@Node("Location")
@Getter
public class Location {

    @Id
    private Integer id;
    private String city;
    private String country;
    private Double longitude;
    private Double latitude;
}
