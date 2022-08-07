package com.exhibitions.modernexhibitions.controller;

import com.exhibitions.modernexhibitions.dto.CentralityDto;
import com.exhibitions.modernexhibitions.service.GDSAlgorithmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for algorithms implemented in the Neo4j GDS library.
 */
@RestController
@RequestMapping(AlgorithmsController.BASE_URL)
public class AlgorithmsController {

    /**
     * Base URL
     */
    static final String BASE_URL = "algorithms";
    private final GDSAlgorithmsService gdsAlgorithmsService;
    private final Logger logger = LoggerFactory.getLogger(AlgorithmsController.class);

    @Autowired
    public AlgorithmsController(GDSAlgorithmsService gdsAlgorithmsService){
        this.gdsAlgorithmsService = gdsAlgorithmsService;
    }

    /**
     * GET: Returns degree centralities of each node in a network
     *
     * @param artistIds the artist ids of the network
     * @param year nullable parameter, year can be specified
     * @return Returns degree centralities of each node in a network
     */
    @GetMapping("/degree")
    public List<CentralityDto> getDegreeCentrality(@RequestParam List<Integer> artistIds, @RequestParam(required=false) Integer year) {
       logger.info("GET degree centrality for IDs: " + artistIds);
        return gdsAlgorithmsService.getDegreeCentrality(artistIds, year);
    }

    /**
     * GET: Returns weighted degree centralities of each node in a network
     *
     * @param artistIds the artist ids of the network
     * @param year nullable parameter, year can be specified
     * @return Returns weighted degree centralities of each node in a network
     */
    @GetMapping("degree/weighted")
    public List<CentralityDto> getWeightedDegreeCentrality(@RequestParam List<Integer> artistIds, @RequestParam(required=false) Integer year) {
        logger.info("GET weighted degree centrality for IDs: " + artistIds);
        return gdsAlgorithmsService.getDegreeCentralityWeighted(artistIds , year);
    }

}
