package com.exhibitions.modernexhibitions.service;

import com.exhibitions.modernexhibitions.dto.CentralityDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface GDSAlgorithmsService {

    List<CentralityDto> getDegreeCentrality(List<Integer> artistIds);

    List<CentralityDto> getDegreeCentralityWeighted(List<Integer> artistIds);

}
