package com.exhibitions.modernexhibitions.repository.projection;

import com.exhibitions.modernexhibitions.entity.ExhibitsWithYearly;

import java.util.List;

public interface ArtistProjectionYearlyNetwork {
    Integer getId();
    List<ExhibitsWithYearly> getCoArtistsIncoming();
    List<ExhibitsWithYearly> getCoArtistsOutgoing();
}
