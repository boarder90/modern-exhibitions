import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {NetworkDto} from "../../dtos/NetworkDto";
import {AllYearlyNetworksDto} from "../../dtos/AllYearlyNetworksDto";
import {map, Observable, ReplaySubject} from "rxjs";
import {YearlyNetworkDto} from "../../dtos/YearlyNetworkDto";

@Injectable({
  providedIn: 'root'
})
export class NetworkService {

  private networkBaseUri: string = environment.Backend + '/network';

  private map$: ReplaySubject<Map<number,YearlyNetworkDto>> = new ReplaySubject<Map<number, YearlyNetworkDto>>(1);
  private hasLinksYearly$: ReplaySubject<Map<number,Map<string, number>>> = new ReplaySubject<Map<number, Map<string,number>>>(1);
  private hasLinks$: ReplaySubject<Map<string, number>> = new ReplaySubject<Map<string,number>>(1);

  constructor(private httpClient: HttpClient) {}

  /**
   * Returns current map of yearly networks as an observable
   */
  getMap(): Observable<Map<number,YearlyNetworkDto>> {
    return this.map$.asObservable();
  }

  /**
   * Sets map of yearly networks
   *
   * @param map
   */
  setMap(map: Map<number,YearlyNetworkDto>) {
    this.map$.next(map);
  }

  /**
   * Sets map: For every yearly network, store all the existing links
   *
   * @param map
   */
  setHasLinksYearly(map: Map<number,Map<string, number>>) {
    this.hasLinksYearly$.next(map);
  }

  /**
   * Returns current map of existing yearly links
   *
   */
  getHasLinksYearly() {
    return this.hasLinksYearly$.asObservable();
  }

  /**
   * Sets map: For every total network, store all the existing links
   *
   * @param map
   */
  setHasLinks(map: Map<string, number>) {
    this.hasLinks$.next(map);
  }

  /**
   * Returns current map of existing total links
   */
  getHasLinks() {
    return this.hasLinks$.asObservable();
  }

  /**
   * Returns total ego network
   *
   * @param id id of the respective artist
   * @param numExhibitions optional filter which sets the minimum amount of exhibitions (i.e. weight) required
   */
  getTotalEgoNetwork(id: number, numExhibitions: number | null){
    let params = new HttpParams();
    if(numExhibitions!== null){
      params = params.append('numExhibitions', numExhibitions);
    }
    return this.httpClient.get<NetworkDto>(this.networkBaseUri + `/${id}`, {params: params})
  }

  /**
   * Returns a total artist network by its IDs.
   *
   * @param ids ids of the artists / nodes of the network
   */
  getNetwork(ids: number[]){
    let params = new HttpParams();
    if(ids!==null){
      ids.forEach( id => {params = params.append("ids",id)});
    }
    return this.httpClient.get<NetworkDto>(this.networkBaseUri, {params: params}).pipe(
      map((network: NetworkDto)=>{
        let hasLink = new Map();
        network.links.forEach(
          link=>{
            hasLink.set(link.source + "," + link.target,1)
            hasLink.set(link.target + "," + link.source,1)
          }
        )
        this.setHasLinks(hasLink);
        return network;
      }
    ));
  }

  /**
   * Returns network filtered by a country
   *
   * @param ids ids of the artists / nodes of the network
   * @param country name of the country
   */
  getNetworkFilteredByCountry(ids: number[], country: string){
    let params = new HttpParams();
    if(ids!==null){
      ids.forEach( id => {params = params.append("ids",id)});
    }
    params = params.append("country",country);
    return this.httpClient.get<NetworkDto>(this.networkBaseUri + "/country", {params: params});
  }

  /**
   * Returns network filtered by a city
   *
   * @param ids ids of the artists / nodes of the network
   * @param city name of the city
   */
  getNetworkFilteredByCity(ids: number[], city: string){
    let params = new HttpParams();
    if(ids!==null){
      ids.forEach( id => {params = params.append("ids",id)});
    }
    params = params.append("city",city);
    return this.httpClient.get<NetworkDto>(this.networkBaseUri + "/city", {params: params});
  }

  /**
   * Returns yearly network filtered by a country
   *
   * @param ids ids of the artists / nodes of the network
   * @param country name of the country
   */
  getNetworkYearlyFilteredByCountry(ids: number[], country: string){
    let params = new HttpParams();
    if(ids!==null){
      ids.forEach( id => {params = params.append("ids",id)});
    }
    params = params.append("country",country);
    this.httpClient.get<AllYearlyNetworksDto[]>(this.networkBaseUri + "/yearly/all/country" ,{params: params}).subscribe(
      data => {
        let map = new Map();
        data.forEach(
          network => {
            map.set(Number(network.year), network.network);
          }
        )
        this.setMap(map)
      }
    )
  }

  /**
   * Returns yearly network filtered by a city
   *
   * @param ids ids of the artists / nodes of the network
   * @param city name of the city
   */
  getNetworkYearlyFilteredByCity(ids: number[], city: string){
    let params = new HttpParams();
    if(ids!==null){
      ids.forEach( id => {params = params.append("ids",id)});
    }
    params = params.append("city",city);
    this.httpClient.get<AllYearlyNetworksDto[]>(this.networkBaseUri + "/yearly/all/city" ,{params: params}).subscribe(
      data => {
        let map = new Map();
        let yearlyLinks = new Map();
        data.forEach(
          network => {
            map.set(Number(network.year), network.network);
            let hasLink = new Map();
            network.network.links.forEach(
              link => {
                yearlyLinks.set(Number(network.year),hasLink.set(link.source + "," + link.target,1));
                yearlyLinks.set(Number(network.year),hasLink.set(link.target + "," + link.source,1));
              }
            )
          }
        )
        this.setHasLinksYearly(yearlyLinks);
        this.setMap(map)
      }
    )
  }

  /**
   * Returns yearly networks
   *
   * @param ids ids of the artists / nodes of the network
   */
  getNetworkYears(ids: number[]){
    let params = new HttpParams();
    if(ids!==null){
      ids.forEach( id => {params = params.append("ids",id)});
    }

    this.httpClient.get<AllYearlyNetworksDto[]>(this.networkBaseUri + "/yearly/all" ,{params: params}).subscribe(
      data => {
        let map = new Map();
        let yearlyLinks = new Map();
        data.forEach(
          network => {
            map.set(Number(network.year), network.network);
            let hasLink = new Map();
            network.network.links.forEach(
              link => {
                yearlyLinks.set(Number(network.year),hasLink.set(link.source + "," + link.target,1));
                yearlyLinks.set(Number(network.year),hasLink.set(link.target + "," + link.source,1));
              }
            )
          }
        )
        this.setMap(map)
        this.setHasLinksYearly(yearlyLinks);
      }
    )
  }
}
