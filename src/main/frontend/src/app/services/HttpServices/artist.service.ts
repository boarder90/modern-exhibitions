import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {ArtistDto} from "../../dtos/ArtistDto";

@Injectable({
  providedIn: 'root'
})
export class ArtistService {

  constructor(private httpClient: HttpClient) { }

  private artistBaseUri: string = environment.Backend + '/artists';

  /**
   * returns artists by name or part of a name
   *
   * @param name name of the artist
   */
  getArtistsByName(name: string){
    const params = new HttpParams().set('name',name);
    console.log(params)
    return this.httpClient.get<ArtistDto[]>(this.artistBaseUri + '/name', {params: params});
  }

  /**
   * returns an artist by a particular id
   *
   * @param id
   */
  getArtistsById(id: number){
    return this.httpClient.get<ArtistDto>(this.artistBaseUri + `/${id}`);
  }

  /**
   * returns artists by ids
   *
   * @param ids
   */
  getArtistsByIds(ids: number[]){
    let params = new HttpParams();
    if(ids!== null){
      ids.forEach(elem => {params = params.append("ids", elem)});
    }
    return this.httpClient.get<ArtistDto[]>(this.artistBaseUri + "/ids", {params: params});
  }
}
