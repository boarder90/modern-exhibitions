package com.exhibitions.modernexhibitions.controller;

import com.exhibitions.modernexhibitions.dto.ExhibitionDto;
import com.exhibitions.modernexhibitions.dto.FeatureCollectionDto;
import com.exhibitions.modernexhibitions.service.ExhibitionService;
import com.exhibitions.modernexhibitions.service.NonDomainService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ExhibitionController.BASE_URL)
public class ExhibitionController {
    private final ModelMapper modelMapper;
    private final NonDomainService nonDomainService;
    private final ExhibitionService exhibitionService;
    static final String BASE_URL = "exhibitions";

    @Autowired
    public ExhibitionController(ModelMapper modelMapper, ExhibitionService exhibitionService, NonDomainService nonDomainService){
        this.modelMapper = modelMapper;
        this.exhibitionService = exhibitionService;
        this.nonDomainService = nonDomainService;
    }

    @GetMapping()
    public List<ExhibitionDto> getAllExhibitions() {
         return exhibitionService.findAll().stream()
                .map(entity -> modelMapper.map(entity, ExhibitionDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/locations")
    public FeatureCollectionDto getFeatureCollection(@RequestParam(required = false) List<Integer> artistIds) {
        return nonDomainService.getExhibitionLocationsAsGeoJSON(artistIds);
    }

    @GetMapping("/locations/yearly")
    public FeatureCollectionDto getYearlyFeatureCollection(@RequestParam(required = false) List<Integer> artistIds) {
        return nonDomainService.getExhibitionLocationsYearlyAsGeoJSON(artistIds);
    }

    @GetMapping("/filtered")
    public List<ExhibitionDto> getFilteredExhibitions(
            @RequestParam(required = false) List<Double> coordinates,
            @RequestParam(required=false) List<Integer> artistIds,
            @RequestParam(required=false) Integer year)
    {
        return exhibitionService.findExhibitionsFiltered(coordinates,artistIds,year).stream()
                .map(entity -> modelMapper.map(entity, ExhibitionDto.class))
                .collect(Collectors.toList());
    }
}
