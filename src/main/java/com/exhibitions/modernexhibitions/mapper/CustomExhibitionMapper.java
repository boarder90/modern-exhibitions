package com.exhibitions.modernexhibitions.mapper;

import com.exhibitions.modernexhibitions.dto.ExhibitionDto;
import com.exhibitions.modernexhibitions.dto.ExhibitionsByLocationDto;
import com.exhibitions.modernexhibitions.entity.Exhibition;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CustomExhibitionMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public CustomExhibitionMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    public List<ExhibitionsByLocationDto> exhibitionsToExhibitionsByLocationDto(List<Exhibition> exhibitions){
        HashMap<ArrayList<Double>,List<ExhibitionDto>> map = new HashMap<>();
        List<ExhibitionsByLocationDto> exhibitionsByLocationDtos = new ArrayList<>();
        List<ExhibitionDto> exhibitionDtos = exhibitions.stream()
                .map(entity -> modelMapper.map(entity, ExhibitionDto.class)).toList();
        for (ExhibitionDto e:exhibitionDtos) {
            ArrayList<Double> key = new ArrayList<Double>();
            if(e.getLocations().size()!=0) {
               key.add(0, e.getLocations().get(0).getLongitude());
               key.add(1, e.getLocations().get(0).getLatitude());
            } else {
                key.add(0, null);
                key.add(0, null);
            }
            List<ExhibitionDto> exhibitionsOfLocation;
            if(map.containsKey(key)){
                exhibitionsOfLocation = map.get(key);
            } else {
                exhibitionsOfLocation = new ArrayList<>();
            }
            exhibitionsOfLocation.add(e);
            map.put(key,exhibitionsOfLocation);
        }

        Iterator<Map.Entry<ArrayList<Double>, List<ExhibitionDto>>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<ArrayList<Double>, List<ExhibitionDto>> pair = it.next();
            exhibitionsByLocationDtos.add(new ExhibitionsByLocationDto(pair.getKey().get(0),pair.getKey().get(1),pair.getValue()));
            it.remove(); // avoids a ConcurrentModificationException
        }

        return exhibitionsByLocationDtos;
    }
}
