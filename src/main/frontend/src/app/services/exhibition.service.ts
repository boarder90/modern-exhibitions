import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {GeoJSON} from "leaflet";
import {ExhibitionDto} from "../dtos/ExhibitionDto";

@Injectable({
  providedIn: 'root'
})
export class ExhibitionService {

  constructor(private httpClient: HttpClient) { }

  private exhibitionBaseUri: string = environment.Backend + '/exhibitions';

  getExhibitions(){
    return this.httpClient.get<ExhibitionDto[]>(this.exhibitionBaseUri)
  }

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

  getExhibitionLocations(){
    return this.httpClient.get<GeoJSON.FeatureCollection>(this.exhibitionBaseUri + '/locations')
  }

  getExhibitionLocationsYearly() {
    return this.httpClient.get<GeoJSON.FeatureCollection>(this.exhibitionBaseUri + '/locations/yearly')
  }
}

