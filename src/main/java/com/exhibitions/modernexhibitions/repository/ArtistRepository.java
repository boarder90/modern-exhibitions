package com.exhibitions.modernexhibitions.repository;

import com.exhibitions.modernexhibitions.entity.Artist;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjection;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjectionTotalNetwork;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjectionYearlyNetwork;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends Neo4jRepository<Artist,Integer> {

    Optional<ArtistProjection> getArtistById(Integer id);

    @Query("MATCH (a1:Artist) WHERE a1.id in $ids RETURN a1")
    List<ArtistProjection> getArtistsByByIds(List<Integer> ids);

    @Query("MATCH (a1:Artist) WHERE toLower(a1.name) CONTAINS toLower(?#{#name}) RETURN a1 LIMIT 100;")
    List<ArtistProjection> getArtistsByName(String name);

    @Query("match (a1:Artist)-[e:EXHIBITS_WITH]-(a2:Artist) where a1.id=$id and e.numExhibitions>=$numExhibitions and" +
            " ($year IS NULL OR (e.startYear = $year OR e.endYear= $year)) WITH collect(a1)+collect(a2) as nodez " +
            "UNWIND nodez as c RETURN distinct c")
    List<ArtistProjection> getArtistsOfYearlyEgoNetwork(Integer id, Integer numExhibitions, Integer year);

    @Query("""
            CALL
            {MATCH (a1:Artist)-[t:EXHIBITS_WITH]-(a2:Artist) WHERE a1.id = $id
            RETURN a1 as ego, sum(t.numExhibitions) AS numExhibitions, a2 as artists
            }
            WITH collect(ego) + collect(artists) as nodez, numExhibitions
            WHERE numExhibitions>$numExhibitions
            UNWIND(nodez) as artists
            RETURN DISTINCT artists""")
    List<ArtistProjection> getArtistsOfEgoNetwork(Integer id, Integer numExhibitions);

    @Query("MATCH (a1:Artist{id: $id})-[e:EXHIBITS_WITH]-(a2:Artist) WHERE e.numExhibitions>=$numExhibitions and " +
            "($year IS NULL OR (e.startYear = $year OR e.endYear= $year)) return a1,collect(e),collect(a2)")
    List<ArtistProjectionYearlyNetwork> getEgoNetworkOne(Integer id, Integer numExhibitions, Integer year);

    @Query("""
            MATCH (a1:Artist{id: $id})-[e:EXHIBITS_WITH]-(a2:Artist) WHERE e.numExhibitions>=$numExhibitions and ($year IS NULL OR (e.startYear = $year OR e.endYear = $year)) return a1 as startnodes,collect(e) as links,collect(a2) as endnodes
            UNION
            MATCH (a1:Artist{id: $id})-[e:EXHIBITS_WITH]-(a2:Artist) WHERE e.numExhibitions>=$numExhibitions and ($year IS NULL OR (e.startYear = $year OR e.endYear = $year)) WITH collect(a2.id) as coartists MATCH (a3:Artist)-[f:EXHIBITS_WITH]-(a4:Artist) where a3.id in coartists and a4.id in coartists and id(a3) < id(a4) and f.numExhibitions>=$numExhibitions and ($year IS NULL OR (f.startYear = $year OR f.endYear = $year)) RETURN a3 as startnodes, collect(f) as links, collect(a4) as endnodes""")
    List<ArtistProjectionYearlyNetwork> getEgoNetworkOneHalf(Integer id, Integer numExhibitions, Integer year);

    @Query("MATCH (a1:Artist)-[e:EXHIBITS_WITH]-(a2:Artist) WHERE e.numExhibitions>=$numExhibitions and ($year IS NULL OR (e.startYear = $year OR e.endYear = $year)) and" +
            " a1.id in $ids and a2.id in $ids and id(a1)<id(a2) RETURN a1, collect(e), collect(a2)")
    List<ArtistProjectionYearlyNetwork> getNetworkByIds(List<Integer> ids, Integer numExhibitions, Integer year);

    @Query("MATCH (a1:Artist{id: $id})-[e:EXHIBITS_WITH_TOTAL]-(a2:Artist) WHERE e.numExhibitions>=$numExhibitions" +
            " return a1,collect(e),collect(a2)")
    List<ArtistProjectionTotalNetwork> getTotalEgoNetworkOne(Integer id, Integer numExhibitions);

    @Query("""
            MATCH (a1:Artist{id: $id})-[e:EXHIBITS_WITH_TOTAL]-(a2:Artist) WHERE e.numExhibitions>=$numExhibitions return a1 as startnodes,collect(e) as links,collect(a2) as endnodes
            UNION
            MATCH (a1:Artist{id: $id})-[e:EXHIBITS_WITH_TOTAL]-(a2:Artist) WHERE e.numExhibitions>=$numExhibitions WITH collect(a2.id) as coartists MATCH (a3:Artist)-[f:EXHIBITS_WITH_TOTAL]-(a4:Artist) where a3.id in coartists and a4.id in coartists and id(a3) < id(a4) and f.numExhibitions>=$numExhibitions RETURN a3 as startnodes, collect(f) as links, collect(a4) as endnodes""")
    List<ArtistProjectionTotalNetwork> getTotalEgoNetworkOneHalf(Integer id, Integer numExhibitions);

    @Query("MATCH (a1:Artist)-[e:EXHIBITS_WITH_TOTAL]-(a2:Artist) WHERE e.numExhibitions>=$numExhibitions and" +
            " a1.id in $ids and a2.id in $ids and id(a1)<id(a2) RETURN a1, collect(e), collect(a2)")
    List<ArtistProjectionTotalNetwork> getTotalNetworkByIds(List<Integer> ids, Integer numExhibitions);


}
