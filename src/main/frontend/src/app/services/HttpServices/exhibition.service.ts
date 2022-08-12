import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {GeoJSON} from "leaflet";
import {ExhibitionDto} from "../../dtos/ExhibitionDto";

@Injectable({
  providedIn: 'root'
})
export class ExhibitionService {

  constructor(private httpClient: HttpClient) { }

  private exhibitionBaseUri: string = environment.Backend + '/exhibitions';

  /**
   * Returns all exhibitions
   */
  getExhibitions(){
    return this.httpClient.get<ExhibitionDto[]>(this.exhibitionBaseUri)
  }

  /**
   * Returns all exhibitions filtered by location, year and / or artistIds
   *
   * @param location latitude / longitude
   * @param year
   * @param artistIds
   */
  getExhibitionsFiltered(location: (number[]|null), year: (number|null), artistIds: (number[]|null)){
    let params = new HttpParams();
    if(year!== null){
    params = params.append('year', year);
    }
    if(location!== null){
        location.forEach(elem => {params = params.append("coordinates", elem)});
    }
    if(artistIds!==null){
      artistIds.forEach( id => {params = params.append("artistIds",id)});
    }
    return this.httpClient.get<ExhibitionDto[]>(this.exhibitionBaseUri + '/filtered', {params: params});
  }

  /**
   * Returns all exhibition locations as a GeoJSON FeatureCollection
   *
   * @param artistIds optional parameter. Filter the exhibitions by a given artist network.
   */
  getExhibitionLocations(artistIds: (number[]|null)){

    let params = new HttpParams();
    if(artistIds!==null){
      console.log(artistIds)
      artistIds.forEach( id => {params = params.append("artistIds",id)});
    }

    return this.httpClient.get<GeoJSON.FeatureCollection>(this.exhibitionBaseUri + '/locations', {params: params})
  }

  /**
   * Returns all exhibition locations ordered by their year as a GeoJSON FeatureCollection
   *
   * @param artistIds optional parameter. Filter the exhibitions by a given artist network.
   */
  getExhibitionLocationsYearly(artistIds: (number[]|null)) {

    let params = new HttpParams();
    if(artistIds!==null){
      artistIds.forEach( id => {params = params.append("artistIds",id)});
    }

    return this.httpClient.get<GeoJSON.FeatureCollection>(this.exhibitionBaseUri + '/locations/yearly', {params: params})
  }
}

