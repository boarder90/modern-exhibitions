package com.exhibitions.modernexhibitions.controller;

import com.exhibitions.modernexhibitions.dto.ExhibitionDto;
import com.exhibitions.modernexhibitions.service.ExhibitionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ExhibitionController.BASE_URL)
public class ExhibitionController {
    private final ModelMapper modelMapper;
    private final ExhibitionService exhibitionService;
    static final String BASE_URL = "exhibitions";

    @Autowired
    public ExhibitionController(ModelMapper modelMapper, ExhibitionService exhibitionService){
        this.modelMapper = modelMapper;
        this.exhibitionService = exhibitionService;
    }

    @GetMapping()
    public List<ExhibitionDto> getAllExhibitions() {
        return exhibitionService.findAll().stream()
                .map(entity -> modelMapper.map(entity, ExhibitionDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/filtered")
    public List<ExhibitionDto> getFilteredExhibitions(
            @RequestParam(required=false) List<Integer> artistIds,
            @RequestParam(required=false) Integer year
    ) {
        return exhibitionService.findExhibitionsFiltered(artistIds,year).stream()
                .map(entity -> modelMapper.map(entity, ExhibitionDto.class))
                .collect(Collectors.toList());
    }
}
