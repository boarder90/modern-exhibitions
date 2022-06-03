package com.exhibitions.modernexhibitions.entity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@ToString
@Node("Location")
public class Location {

    @Id
    private Long id;

    @Getter
    private String venue;

    @Getter
    private String city;

    @Getter
    private String country;

    @Getter
    private Double longitude;

    @Getter
    private Double latitude;
}
