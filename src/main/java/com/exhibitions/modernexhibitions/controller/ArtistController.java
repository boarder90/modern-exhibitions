package com.exhibitions.modernexhibitions.controller;

import com.exhibitions.modernexhibitions.dto.ArtistDto;
import com.exhibitions.modernexhibitions.service.ArtistService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Artists Controller
 */
@RestController
@RequestMapping(ArtistController.BASE_URL)
public class ArtistController {
    /**
     * BASE_URL
     */
    static final String BASE_URL = "artists";
    private final ModelMapper modelMapper;
    private final ArtistService artistService;
    private final Logger logger = LoggerFactory.getLogger(AlgorithmsController.class);

    @Autowired
    public ArtistController(ModelMapper modelMapper, ArtistService artistService){
        this.modelMapper = modelMapper;
        this.artistService = artistService;
    }

    /**
     * GET: Returns an ArtistDto by an id
     *
     * @param id the id of the artist
     * @return Returns an ArtistDto
     */
    @GetMapping("/{id}")
    public ArtistDto getArtistById(@PathVariable Integer id) {
        logger.info("GET artists by ID: " + id);
        return modelMapper.map(this.artistService.getArtistById(id), ArtistDto.class);
    }

    /**
     * GET: Return a list of ArtistDtos by ids
     *
     * @param ids the ids of the artists
     * @return Returns a list of ArtistDtos
     */
    @GetMapping("/ids")
    public List<ArtistDto> getArtistByIds(@RequestParam List<Integer> ids) {
        logger.info("GET artists by IDs: " + ids);
        return artistService.getArtistsByIds(ids).stream().
                map(entity -> modelMapper.map(entity, ArtistDto.class)).collect(Collectors.toList());
    }

    /**
     * GET: Return a list of ArtistDtos by name
     * @param name (part of the) name of the artists
     * @return Returns a list of ArtistDtos
     */
    @GetMapping("/name")
    public List<ArtistDto> getArtistByName(@RequestParam() String name) {
        logger.info("GET artist by name: " + name);
        return artistService.getArtistsByName(name).stream()
                .map(entity -> modelMapper.map(entity, ArtistDto.class))
                .collect(Collectors.toList());
    }

    /**
     * GET: Return a list of ArtistDtos for an ego network
     *
     * @param id ID of the ego
     * @param numExhibitions optional parameter to filter by the minimum number of exhibitions
     * @param year parameter to get specific year
     * @return Return a list of ArtistDtos
     */
    @GetMapping("/ego/{id}")
    public List<ArtistDto> getArtistsOfYearlyEgoNetwork(@PathVariable Integer id,
                                                  @RequestParam(required = false, defaultValue = "0") Integer numExhibitions,
                                                  @RequestParam Integer year) {
        logger.info("GET ego network of artist with id: " + id);
        return artistService.getArtistsOfYearlyEgoNetwork(id,numExhibitions,year).stream()
                .map(entity -> modelMapper.map(entity, ArtistDto.class))
                .collect(Collectors.toList());
    }
}
