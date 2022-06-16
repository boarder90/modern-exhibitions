package com.exhibitions.modernexhibitions.controller;

import com.exhibitions.modernexhibitions.dto.ArtistDto;
import com.exhibitions.modernexhibitions.dto.ArtistNetworkDto;
import com.exhibitions.modernexhibitions.dto.YearlyArtistNetworkDto;
import com.exhibitions.modernexhibitions.mapper.CustomNetworkMapper;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjectionTotalNetwork;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjectionYearlyNetwork;
import com.exhibitions.modernexhibitions.service.ArtistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(NetworkController.BASE_URL)
public class NetworkController {

    static final String BASE_URL = "network";
    private final ModelMapper modelMapper;
    private final ArtistService artistService;
    private final CustomNetworkMapper customNetworkMapper;

    @Autowired
    public NetworkController(ModelMapper modelMapper, ArtistService artistService, CustomNetworkMapper customNetworkMapper){
        this.modelMapper = modelMapper;
        this.artistService = artistService;
        this.customNetworkMapper = customNetworkMapper;
    }

    @GetMapping("/yearly/{id}")
    public YearlyArtistNetworkDto getYearlyEgoNetwork(@PathVariable Integer id,
                                                      @RequestParam(required = false, defaultValue = "0") Integer numExhibitions,
                                                      @RequestParam(required = false) Integer year) {
        List<ArtistProjectionYearlyNetwork> artists = artistService.getYearlyEgoNetworkOne(id,numExhibitions,year);


        return new YearlyArtistNetworkDto(artistService.getArtistsOfYearlyEgoNetwork(id,numExhibitions,year).stream()
                .map(entity -> modelMapper.map(entity, ArtistDto.class))
                .collect(Collectors.toList()), customNetworkMapper.artistsToYearlyLinkDtoList(artists));
    }

    @GetMapping("/yearly/onehalf/{id}")
    public YearlyArtistNetworkDto getYearlyEgoOneHalfNetwork(@PathVariable Integer id,
                                                             @RequestParam(required = false, defaultValue = "0") Integer numExhibitions,
                                                             @RequestParam(required = false) Integer year) {
        List<ArtistProjectionYearlyNetwork> artists = artistService.getYearlyEgoNetworkOneHalf(id,numExhibitions,year);
        return new YearlyArtistNetworkDto(artistService.getArtistsOfYearlyEgoNetwork(id,numExhibitions,year).stream()
                .map(entity -> modelMapper.map(entity, ArtistDto.class))
                .collect(Collectors.toList()), customNetworkMapper.artistsToYearlyLinkDtoList(artists));
    }

    @GetMapping("yearly")
    public YearlyArtistNetworkDto getYearlyNetworkByIds(@RequestParam() List<Integer> ids,
                                                        @RequestParam(required = false, defaultValue = "0") Integer numExhibitions,
                                                        @RequestParam(required = false) Integer year) {
        List<ArtistProjectionYearlyNetwork> artists = artistService.getNetworkByIds(ids,numExhibitions,year);
        return new YearlyArtistNetworkDto(artistService.getArtistsByIds(ids).stream()
                .map(entity -> modelMapper.map(entity, ArtistDto.class))
                .collect(Collectors.toList()), customNetworkMapper.artistsToYearlyLinkDtoList(artists));
    }

    @GetMapping("/{id}")
    public ArtistNetworkDto getEgoNetwork(@PathVariable Integer id,
                                          @RequestParam(required = false, defaultValue = "0") Integer numExhibitions) {
        List<ArtistProjectionTotalNetwork> artists = artistService.getTotalEgoNetworkOne(id,numExhibitions);
        return new ArtistNetworkDto(artistService.getArtistsOfEgoNetwork(id,numExhibitions).stream().map(entity -> modelMapper.map(entity, ArtistDto.class)).collect(Collectors.toList()),
                customNetworkMapper.artistsToLinkDtoList(artists));
    }

    @GetMapping("/onehalf/{id}")
    public ArtistNetworkDto getEgoOneHalfNetwork(@PathVariable Integer id,
                                                 @RequestParam(required = false, defaultValue = "0") Integer numExhibitions) {
        List<ArtistProjectionTotalNetwork> artists = artistService.getTotalEgoNetworkOneHalf(id,numExhibitions);
        return new ArtistNetworkDto(artistService.getArtistsOfEgoNetwork(id,numExhibitions).stream().map(entity -> modelMapper.map(entity, ArtistDto.class)).collect(Collectors.toList()),
                customNetworkMapper.artistsToLinkDtoList(artists));
    }

    @GetMapping()
    public ArtistNetworkDto getNetworkByIds(@RequestParam() List<Integer> ids,
                                            @RequestParam(required = false, defaultValue = "0") Integer numExhibitions) {
        List<ArtistProjectionTotalNetwork> artists = artistService.getTotalNetworkByIds(ids,numExhibitions);
        return new ArtistNetworkDto(artistService.getArtistsByIds(ids).stream().map(entity -> modelMapper.map(entity, ArtistDto.class)).collect(Collectors.toList()),
                customNetworkMapper.artistsToLinkDtoList(artists));
    }


}
