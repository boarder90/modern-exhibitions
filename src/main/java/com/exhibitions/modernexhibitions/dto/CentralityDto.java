package com.exhibitions.modernexhibitions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO, which can be used for all kinds of centrality measures
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class CentralityDto {
    private Integer id;
    private Double centrality;
}
