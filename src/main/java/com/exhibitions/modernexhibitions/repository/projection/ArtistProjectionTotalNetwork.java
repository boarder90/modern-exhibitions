package com.exhibitions.modernexhibitions.repository.projection;

import com.exhibitions.modernexhibitions.entity.ExhibitsWithTotal;

import java.util.List;

public interface ArtistProjectionTotalNetwork {
    Integer getId();
    List<ExhibitsWithTotal> getCoArtistsTotalIncoming();
    List<ExhibitsWithTotal> getCoArtistsTotalOutgoing();
}
