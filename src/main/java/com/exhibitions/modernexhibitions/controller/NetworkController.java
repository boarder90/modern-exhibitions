package com.exhibitions.modernexhibitions.controller;

import com.exhibitions.modernexhibitions.dto.*;
import com.exhibitions.modernexhibitions.mapper.CustomNetworkMapper;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjectionTotalNetwork;
import com.exhibitions.modernexhibitions.repository.projection.ArtistProjectionYearlyNetwork;
import com.exhibitions.modernexhibitions.service.ArtistService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Network controller
 */
@RestController
@RequestMapping(NetworkController.BASE_URL)
public class NetworkController {

    /**
     * Base URL
     */
    static final String BASE_URL = "network";
    private final ModelMapper modelMapper;
    private final ArtistService artistService;
    private final CustomNetworkMapper customNetworkMapper;
    private final static Integer[] YEARS = {1904,1905,1906,1907,1908,1909,1910,1911,1912,1913,1914,1915,1916};
    private final Logger logger = LoggerFactory.getLogger(NetworkController.class);

    @Autowired
    public NetworkController(ModelMapper modelMapper, ArtistService artistService, CustomNetworkMapper customNetworkMapper){
        this.modelMapper = modelMapper;
        this.artistService = artistService;
        this.customNetworkMapper = customNetworkMapper;
    }

    /**
     * GET: Returns an ego network DTO consisting of nodes and yearly links
     *
     * @param id id of the desired artist
     * @param numExhibitions optional filter to determine the minimum number of exhibitions (i.e., weight) of the links
     * @param year optional parameter year to get a specific year
     * @return an ego network DTO consisting of nodes and yearly links
     */
    @GetMapping("/yearly/{id}")
    public YearlyArtistNetworkDto getYearlyEgoNetwork(@PathVariable Integer id,
                                                      @RequestParam(required = false, defaultValue = "0") Integer numExhibitions,
                                                      @RequestParam(required = false) Integer year) {
        List<ArtistProjectionYearlyNetwork> artists = artistService.getYearlyEgoNetworkOne(id,numExhibitions,year);
        logger.info("GET yearly ego network of artist with ID :" + id);

        return new YearlyArtistNetworkDto(artistService.getArtistsOfYearlyEgoNetwork(id,numExhibitions,year).stream()
                .map(entity -> modelMapper.map(entity, ArtistDto.class))
                .collect(Collectors.toList()), customNetworkMapper.artistsToYearlyLinkDtoList(artists));
    }

    /**
     * GET: Returns an 1,5 ego network DTO consisting of nodes and yearly links
     *
     * @param id id of the desired artist
     * @param numExhibitions optional filter to determine the minimum number of exhibitions (i.e., weight) of the links
     * @param year optional parameter year to get a specific year
     * @return an 1,5 ego network DTO consisting of nodes and yearly links
     */
    @GetMapping("/yearly/onehalf/{id}")
    public YearlyArtistNetworkDto getYearlyEgoOneHalfNetwork(@PathVariable Integer id,
                                                             @RequestParam(required = false, defaultValue = "0") Integer numExhibitions,
                                                             @RequestParam(required = false) Integer year) {
        List<ArtistProjectionYearlyNetwork> artists = artistService.getYearlyEgoNetworkOneHalf(id,numExhibitions,year);
        logger.info("GET 1.5 yearly ego network of artist with ID: " + id);
        return new YearlyArtistNetworkDto(artistService.getArtistsOfYearlyEgoNetwork(id,numExhibitions,year).stream()
                .map(entity -> modelMapper.map(entity, ArtistDto.class))
                .collect(Collectors.toList()), customNetworkMapper.artistsToYearlyLinkDtoList(artists));
    }

    /**
     * GET: Returns a yearly artist network DTO consisting of nodes with the given IDs
     *
     * @param ids ids of the artists (nodes)
     * @param numExhibitions optional filter to determine the minimum number of exhibitions (i.e., weight) of the links
     * @param year optional parameter year to get a specific year
     * @return yearly network DTO
     */
    @GetMapping("yearly")
    public YearlyArtistNetworkDto getYearlyNetworkByIds(@RequestParam() List<Integer> ids,
                                                        @RequestParam(required = false, defaultValue = "0") Integer numExhibitions,
                                                        @RequestParam(required = false) Integer year) {
        List<ArtistProjectionYearlyNetwork> artists = artistService.getYearlyNetworkByIds(ids,numExhibitions,year);
        logger.info("GET yearly network with IDs: " + ids + " and minimum number of exhibitions " + numExhibitions + " and year: " + year);
        return new YearlyArtistNetworkDto(artistService.getArtistsByIds(ids).stream()
                .map(entity -> modelMapper.map(entity, ArtistDto.class))
                .collect(Collectors.toList()), customNetworkMapper.artistsToYearlyLinkDtoList(artists));
    }

    /**
     * GET: Returns all yearly artist network DTOs consisting of nodes with the given IDs with all the respective years in the database
     *
     * @param ids of the artists (nodes)
     * @param numExhibitions optional filter to determine the minimum number of exhibitions (i.e., weight) of the links
     * @return all yearly network DTOs
     */
    @GetMapping("yearly/all")
    public List<ArtistNetworkAllYearsDto> getAllYearlyNetworks(@RequestParam() List<Integer> ids,
                                               @RequestParam(required = false, defaultValue = "0") Integer numExhibitions) {

        List<ArtistDto> artistDtos = artistService.getArtistsByIds(ids).stream().
                map(entity -> modelMapper.map(entity, ArtistDto.class)).toList();
        List<ArtistNetworkAllYearsDto> networks = new ArrayList<>();
        for (Integer y:YEARS) {
            networks.add(new ArtistNetworkAllYearsDto(y, new YearlyArtistNetworkDto(artistDtos,
                    customNetworkMapper.artistsToYearlyLinkDtoList(artistService.getYearlyNetworkByIds(ids,numExhibitions,y)))));
        }
        logger.info("GET yearly networks with IDs: " + ids + " and minimum number of exhibitions: " + numExhibitions);
        return networks;
    }

    /**
     * GET: Returns an ego network DTO consisting of nodes and total links
     *
     * @param id id of the desired artist
     * @param numExhibitions optional filter to determine the minimum number of exhibitions (i.e., weight) of the links
     * @return an ego network DTO consisting of nodes and total links
     */
    @GetMapping("/{id}")
    public ArtistNetworkDto getEgoNetwork(@PathVariable Integer id,
                                          @RequestParam(required = false, defaultValue = "0") Integer numExhibitions) {
        List<ArtistProjectionTotalNetwork> artists = artistService.getTotalEgoNetworkOne(id,numExhibitions);
        logger.info("GET total ego network of artist with ID: " + id + " and minimum number of exhibitions " + numExhibitions);
        return new ArtistNetworkDto((artistService.getArtistsOfEgoNetwork(id,numExhibitions)).stream().map(entity -> modelMapper.map(entity, ArtistDto.class)).toList(),
                customNetworkMapper.artistsToLinkDtoList(artists));
    }

    /**
     * GET: Returns an 1,5 ego network DTO consisting of nodes and total links
     *
     * @param id id of the desired artist
     * @param numExhibitions optional filter to determine the minimum number of exhibitions (i.e., weight) of the links
     * @return an 1,5 ego network DTO consisting of nodes and total links
     */
    @GetMapping("/onehalf/{id}")
    public ArtistNetworkDto getEgoOneHalfNetwork(@PathVariable Integer id,
                                                 @RequestParam(required = false, defaultValue = "0") Integer numExhibitions) {
        List<ArtistProjectionTotalNetwork> artists = artistService.getTotalEgoNetworkOneHalf(id,numExhibitions);
        logger.info("GET total 1,5 ego network of artist with ID: " + id + " and minimum number of exhibitions " + numExhibitions);
        return new ArtistNetworkDto(artistService.getArtistsOfEgoNetwork(id,numExhibitions).stream().map(entity -> modelMapper.map(entity, ArtistDto.class)).collect(Collectors.toList()),
                customNetworkMapper.artistsToLinkDtoList(artists));
    }

    /**
     * GET: Returns a total artist network DTO consisting of nodes with the given IDs
     *
     * @param ids ids of the artists (nodes)
     * @param numExhibitions optional filter to determine the minimum number of exhibitions (i.e., weight) of the links
     * @return total network DTO
     */
    @GetMapping()
    public ArtistNetworkDto getTotalNetworkByIds(@RequestParam() List<Integer> ids,
                                            @RequestParam(required = false, defaultValue = "0") Integer numExhibitions) {
        List<ArtistProjectionTotalNetwork> artists = artistService.getTotalNetworkByIds(ids,numExhibitions);
        logger.info("GET total network by IDs: " + ids +" and minimum number of exhibitions " + numExhibitions);
        return new ArtistNetworkDto(artistService.getArtistsByIds(ids).stream().map(entity -> modelMapper.map(entity, ArtistDto.class)).collect(Collectors.toList()),
                customNetworkMapper.artistsToLinkDtoList(artists));
    }

    /**
     * GET: Returns a total artist network DTO consisting of nodes with the given IDs, filtered by a specific country
     *
     * @param ids ids of the artists (nodes)
     * @param country country which is used as a filter
     * @return total network DTO
     */
    @GetMapping("/country")
    public ArtistNetworkDto getTotalNetworkByIdsFilteredByCountry(@RequestParam() List<Integer> ids,
                                                 @RequestParam() String country) {
        List<ArtistProjectionTotalNetwork> artists = artistService.getTotalNetworkByIdsFilteredByCountry(ids,country);
       logger.info("GET total network by IDs: " + ids + " and filtered by country " + country);
        return new ArtistNetworkDto(artistService.getArtistsByIds(ids).stream().map(entity -> modelMapper.map(entity, ArtistDto.class)).collect(Collectors.toList()),
                customNetworkMapper.artistsToLinkDtoList(artists));
    }

    /**
     * GET: Returns a total artist network DTO consisting of nodes with the given IDs, filtered by a specific city
     *
     * @param ids ids of the artists (nodes)
     * @param city city which is used as a filter
     * @return total network DTO
     */
    @GetMapping("/city")
    public ArtistNetworkDto getTotalNetworkByIdsFilteredByCity(@RequestParam() List<Integer> ids,
                                                                  @RequestParam() String city) {
        List<ArtistProjectionTotalNetwork> artists = artistService.getTotalNetworkByIdsFilteredByCity(ids,city);
        logger.info("GET total network by IDs: " + ids + " and filtered by city " + city);
        return new ArtistNetworkDto(artistService.getArtistsByIds(ids).stream().map(entity -> modelMapper.map(entity, ArtistDto.class)).collect(Collectors.toList()),
                customNetworkMapper.artistsToLinkDtoList(artists));
    }

    /**
     * GET: Returns all yearly artist network DTOs consisting of nodes with the given IDs with all the respective years in the database,
     * filtered by a specific country
     *
     * @param ids of the artists (nodes)
     * @param country country which is used as a filter
     * @return all yearly network DTOs
     */
    @GetMapping("yearly/all/country")
    public List<ArtistNetworkAllYearsDto> getAllYearlyNetworksFilteredByCountry(@RequestParam() List<Integer> ids,
                                                               @RequestParam() String country) {

        List<ArtistDto> artistDtos = artistService.getArtistsByIds(ids).stream().
                map(entity -> modelMapper.map(entity, ArtistDto.class)).toList();
        List<ArtistNetworkAllYearsDto> networks = new ArrayList<>();
        for (Integer y:YEARS) {
            networks.add(new ArtistNetworkAllYearsDto(y, new YearlyArtistNetworkDto(artistDtos,
                    customNetworkMapper.artistsToYearlyLinkDtoList(artistService.getYearlyNetworkByIdsFilteredByCountry(ids,country,y)))));
        }
        logger.info("GET yearly networks by IDs: " + ids + " and filtered by country " + country);
        return networks;
    }

    /**
     * GET: Returns all yearly artist network DTOs consisting of nodes with the given IDs with all the respective years in the database,
     * filtered by a specific city
     *
     * @param ids of the artists (nodes)
     * @param city city which is used as a filter
     * @return all yearly network DTOs
     */
    @GetMapping("yearly/all/city")
    public List<ArtistNetworkAllYearsDto> getAllYearlyNetworksFilteredByCity(@RequestParam() List<Integer> ids,
                                                                                @RequestParam() String city) {

        List<ArtistDto> artistDtos = artistService.getArtistsByIds(ids).stream().
                map(entity -> modelMapper.map(entity, ArtistDto.class)).toList();
        List<ArtistNetworkAllYearsDto> networks = new ArrayList<>();
        for (Integer y:YEARS) {
            networks.add(new ArtistNetworkAllYearsDto(y, new YearlyArtistNetworkDto(artistDtos,
                    customNetworkMapper.artistsToYearlyLinkDtoList(artistService.getYearlyNetworkByIdsFilteredByCity(ids,city,y)))));
        }
        logger.info("GET yearly networks by IDs: " + ids + " and filtered by city " + city);
        return networks;
    }
}
