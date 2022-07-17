package com.exhibitions.modernexhibitions.controller;

import com.exhibitions.modernexhibitions.dto.LocationsOfNetworkDto;
import com.exhibitions.modernexhibitions.service.NonDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(LocationController.BASE_URL)
public class LocationController {
    static final String BASE_URL = "locations";
    private final NonDomainService nonDomainService;

    @Autowired
    public LocationController(NonDomainService nonDomainService){
        this.nonDomainService = nonDomainService;
    }


    @GetMapping()
    public LocationsOfNetworkDto getLocationsOfNetwork(@RequestParam List<Integer> artistIds) {
        return nonDomainService.getLocationsOfNetwork(artistIds);
    }
}
