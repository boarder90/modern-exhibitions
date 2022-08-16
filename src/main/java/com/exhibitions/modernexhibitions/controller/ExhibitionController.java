package com.exhibitions.modernexhibitions.controller;

import com.exhibitions.modernexhibitions.dto.ExhibitionDto;
import com.exhibitions.modernexhibitions.dto.FeatureCollectionDto;
import com.exhibitions.modernexhibitions.service.ExhibitionService;
import com.exhibitions.modernexhibitions.service.NonDomainService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Exhibition Controller
 */
@RestController
@RequestMapping(ExhibitionController.BASE_URL)
public class ExhibitionController {
    private final ModelMapper modelMapper;
    private final NonDomainService nonDomainService;
    private final ExhibitionService exhibitionService;
    /**
     * BASE_URL
     */
    static final String BASE_URL = "exhibitions";
    private final Logger logger = LoggerFactory.getLogger(ExhibitionController.class);

    @Autowired
    public ExhibitionController(ModelMapper modelMapper, ExhibitionService exhibitionService, NonDomainService nonDomainService){
        this.modelMapper = modelMapper;
        this.exhibitionService = exhibitionService;
        this.nonDomainService = nonDomainService;
    }

    /**
     * GET: Returns all exhibitions
     *
     * @return all exhibitions stored in the database
     */
    @GetMapping()
    public List<ExhibitionDto> getAllExhibitions() {
        logger.info("GET all exhibitions");
         return exhibitionService.findAll().stream()
                .map(entity -> modelMapper.map(entity, ExhibitionDto.class))
                .collect(Collectors.toList());
    }

    /**
     * GET: Returns a FeatureCollectionDto
     *
     * @param artistIds optional filter. If provided, the exhibitions are filtered by an artist network,
     *                  which is given by a list of artist IDs.
     * @return a FeatureCollectionDto, which complies with the GeoJSON standard
     */
    @GetMapping("/locations")
    public FeatureCollectionDto getFeatureCollection(@RequestParam(required = false) List<Integer> artistIds) {
        logger.info("GET all exhibitions as GeoJSONs. Artist network filter: " + artistIds);
        return nonDomainService.getExhibitionLocationsAsGeoJSON(artistIds);
    }

    /**
     * GET: Returns a FeatureCollectionDto, where the exhibitions are ordered by their start years
     *
     * @param artistIds optional filter. If provided, the exhibitions are filtered by an artist network,
     *                  which is given by a list of artist IDs. Only the COMMON exhibitions will be returned!
     * @return a FeatureCollectionDto, which complies with the GeoJSON standard. The exhibitions are ordered by the start years.
     */
    @GetMapping("/locations/yearly")
    public FeatureCollectionDto getYearlyFeatureCollection(@RequestParam(required = false) List<Integer> artistIds) {
        logger.info("GET all exhibitions by year as GeoJSONs");
        return nonDomainService.getExhibitionLocationsYearlyAsGeoJSON(artistIds);
    }

    /**
     * GET: Returns all exhibitions filtered by coordinates, artist IDs and / or year. The filters can be combined arbitrarily.
     *
     * @param coordinates optional parameter. Latitude / longitude given as a list of Double values.
     * @param artistIds optional parameter. Specifies an artist network (i.e., only the COMMON exhibitions will be returned!)
     * @param year optional parameter. Start year of an exhibition.
     * @return all exhibitions filtered by coordinates, artist IDs and / or year
     */
    @GetMapping("/filtered")
    public List<ExhibitionDto> getFilteredExhibitions(
            @RequestParam(required = false) List<Double> coordinates,
            @RequestParam(required=false) List<Integer> artistIds,
            @RequestParam(required=false) Integer year)
    {
        logger.info("GET all exhibitions filtered by coordinates: " + coordinates
                + " and/or artistIds: " + artistIds + " and/or year: " + year);
        return exhibitionService.findExhibitionsFiltered(coordinates,artistIds,year).stream()
                .map(entity -> modelMapper.map(entity, ExhibitionDto.class))
                .collect(Collectors.toList());
    }
}
