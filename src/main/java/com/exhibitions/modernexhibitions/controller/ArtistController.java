package com.exhibitions.modernexhibitions.controller;

import com.exhibitions.modernexhibitions.dto.ArtistDto;
import com.exhibitions.modernexhibitions.service.ArtistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ArtistController.BASE_URL)
public class ArtistController {
    static final String BASE_URL = "artists";
    private final ModelMapper modelMapper;
    private final ArtistService artistService;

    @Autowired
    public ArtistController(ModelMapper modelMapper, ArtistService artistService){
        this.modelMapper = modelMapper;
        this.artistService = artistService;
    }

    @GetMapping("/{id}")
    public ArtistDto getArtistById(@PathVariable Integer id) {
        return modelMapper.map(this.artistService.getArtistById(id), ArtistDto.class);
    }

    @GetMapping("/ids")
    public List<ArtistDto> getArtistByIds(@RequestParam List<Integer> ids) {
        return artistService.getArtistsByIds(ids).stream().
                map(entity -> modelMapper.map(entity, ArtistDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/name")
    public List<ArtistDto> getArtistByName(@RequestParam() String name) {
        return artistService.getArtistsByName(name).stream()
                .map(entity -> modelMapper.map(entity, ArtistDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/ego/{id}")
    public List<ArtistDto> getArtistsOfYearlyEgoNetwork(@PathVariable Integer id,
                                                  @RequestParam(required = false, defaultValue = "0") Integer numExhibitions,
                                                  @RequestParam(required = false) Integer year) {
        return artistService.getArtistsOfYearlyEgoNetwork(id,numExhibitions,year).stream()
                .map(entity -> modelMapper.map(entity, ArtistDto.class))
                .collect(Collectors.toList());
    }
}
