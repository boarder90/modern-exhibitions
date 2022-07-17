package com.exhibitions.modernexhibitions.repository;

import com.exhibitions.modernexhibitions.dto.CentralityDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GDSAlgorithmsRepository {
    @Transactional
    List<CentralityDto> getDegreeCentrality(List<Integer> artistIds);

    @Transactional
    List<CentralityDto> getDegreeCentralityWeighted(List<Integer> artistIds);
}
