package com.exhibitions.modernexhibitions.controller;

import com.exhibitions.modernexhibitions.dto.LocationsOfNetworkDto;
import com.exhibitions.modernexhibitions.service.NonDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Location Controller
 */
@RestController
@RequestMapping(LocationController.BASE_URL)
public class LocationController {
    /**
     * BASE URL
     */
    static final String BASE_URL = "locations";
    private final NonDomainService nonDomainService;
    private final Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    public LocationController(NonDomainService nonDomainService){
        this.nonDomainService = nonDomainService;
    }

    /**
     * GET: Returns all cities and countries of a specified (geospatial) artist network
     *
     * @param artistIds list of IDs which represent the nodes of an artist network
     * @return all the cities and countries of a specified (geospatial) artist network
     */
    @GetMapping()
    public LocationsOfNetworkDto getLocationsOfNetwork(@RequestParam List<Integer> artistIds) {
        logger.info("GET all locations of network: " + artistIds);
        return nonDomainService.getLocationsOfNetwork(artistIds);
    }
}
