import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {LocationsOfNetwork} from "../../dtos/LocationsOfNetwork";

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  private locationBaseUri: string = environment.Backend + '/locations';

  constructor(private httpClient: HttpClient) { }

  getLocationsOfNetwork(artistIds: (number[]|null)){

    let params = new HttpParams();
    if(artistIds!==null){
      artistIds.forEach( id => {params = params.append("artistIds",id)});
    }

    return this.httpClient.get<LocationsOfNetwork>(this.locationBaseUri, {params: params})
  }

}
