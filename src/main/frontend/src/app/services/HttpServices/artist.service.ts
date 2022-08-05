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

  getArtistsByName(name: string){
    const params = new HttpParams().set('name',name);
    console.log(params)
    return this.httpClient.get<ArtistDto[]>(this.artistBaseUri + '/name', {params: params});
  }

  getArtistsById(id: number){
    return this.httpClient.get<ArtistDto>(this.artistBaseUri + `/${id}`);
  }

  getArtistsByIds(ids: number[]){
    let params = new HttpParams();
    if(ids!== null){
      ids.forEach(elem => {params = params.append("ids", elem)});
    }
    return this.httpClient.get<ArtistDto[]>(this.artistBaseUri + "/ids", {params: params});
  }
}
