package com.exhibitions.modernexhibitions.repository.projection;

/**
 * ArtistProjection: This avoids querying all the edges of the artist -
 * Neo4j Spring Data would try to go as deep as possible in the stored grapb
 */
public interface ArtistProjection {
        Integer getId();
        String getName();
        String getNationality();
        String getSex();
        String getOccupation();
    }
