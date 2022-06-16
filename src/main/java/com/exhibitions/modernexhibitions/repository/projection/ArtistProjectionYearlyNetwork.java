package com.exhibitions.modernexhibitions.repository.projection;

import com.exhibitions.modernexhibitions.entity.ExhibitsWith;

import java.util.List;

public interface ArtistProjectionYearlyNetwork {
    Integer getId();
    List<ExhibitsWith> getCoArtistsIncoming();
    List<ExhibitsWith> getCoArtistsOutgoing();
}
