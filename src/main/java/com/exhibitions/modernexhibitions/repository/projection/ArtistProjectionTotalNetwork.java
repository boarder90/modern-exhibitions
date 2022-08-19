package com.exhibitions.modernexhibitions.repository.projection;

import com.exhibitions.modernexhibitions.entity.ExhibitsWithTotal;

import java.util.List;

/**
 * ArtistProjectionTotalNetwork: Avoids querying unnecessary information about an artist
 */
public interface ArtistProjectionTotalNetwork {
    Integer getId();
    List<ExhibitsWithTotal> getCoArtistsTotalIncoming();
    List<ExhibitsWithTotal> getCoArtistsTotalOutgoing();
}
