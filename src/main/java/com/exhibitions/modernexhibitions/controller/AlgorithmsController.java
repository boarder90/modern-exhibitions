package com.exhibitions.modernexhibitions.controller;

import com.exhibitions.modernexhibitions.dto.CentralityDto;
import com.exhibitions.modernexhibitions.dto.LocationsOfNetworkDto;
import com.exhibitions.modernexhibitions.mapper.CustomNetworkMapper;
import com.exhibitions.modernexhibitions.service.ArtistService;
import com.exhibitions.modernexhibitions.service.GDSAlgorithmsService;
import com.exhibitions.modernexhibitions.service.NonDomainService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(AlgorithmsController.BASE_URL)
public class AlgorithmsController {

    static final String BASE_URL = "algorithms";
    private final GDSAlgorithmsService gdsAlgorithmsService;

    @Autowired
    public AlgorithmsController(GDSAlgorithmsService gdsAlgorithmsService){
        this.gdsAlgorithmsService = gdsAlgorithmsService;
    }

    @GetMapping("/degree")
    public List<CentralityDto> getDegreeCentrality(@RequestParam List<Integer> artistIds) {
        return gdsAlgorithmsService.getDegreeCentrality(artistIds);
    }

    @GetMapping("degree/weighted")
    public List<CentralityDto> getLocationsOfNetwork(@RequestParam List<Integer> artistIds) {
        return gdsAlgorithmsService.getDegreeCentralityWeighted(artistIds);
    }

}
