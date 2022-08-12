import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {CentralityDto} from "../../dtos/CentralityDto";

@Injectable({
  providedIn: 'root'
})
export class AlgorithmsService {

  private algorithmsBaseUri: string = environment.Backend + '/algorithms';

  constructor(private httpClient: HttpClient) { }

  /**
   * returns the weighted degree centralities of each node in a given network
   *
   * @param ids artist IDs / node IDs of the network
   * @param year optional parameter year
   */
  getWeightedDegreeCentrality(ids: number[], year: number | null) {
    let params = new HttpParams();
    if (ids !== null) {
      ids.forEach(id => {
        params = params.append("artistIds", id)
      });
    }
    if(year!== null){
      params = params.append('year', year);
    }
    return this.httpClient.get<CentralityDto[]>(this.algorithmsBaseUri + "/degree/weighted", {params: params})
  }

  /**
   * returns the degree centralities of each node in a given network
   *
   * @param ids artist IDs / node IDs of the network
   * @param year optional parameter year
   */
  getUnweightedDegreeCentrality(ids: number[], year: number | null) {
    let params = new HttpParams();
    if (ids !== null) {
      ids.forEach(id => {
        params = params.append("artistIds", id)
      });
    }
    if(year!== null){
      params = params.append('year', year);
    }
    return this.httpClient.get<CentralityDto[]>(this.algorithmsBaseUri + "/degree", {params: params})
  }
}
