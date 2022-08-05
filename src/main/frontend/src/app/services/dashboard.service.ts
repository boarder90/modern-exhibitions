import { Injectable } from '@angular/core';
import {ArtistService} from "./HttpServices/artist.service";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  countriesMap: Map<string, number> = new Map;
  citiesMap: Map<string, number> = new Map;
  selectedArtists: string = "Nothing selected";

  constructor(private artistService: ArtistService) { }

  getArtistsForNetwork(ids: number[]): string{
    this.artistService.getArtistsByIds(ids).subscribe(
      data => {
        if(data.length>1){
          this.selectedArtists = data[0].name.split(" ")[data[0].name.split(" ").length-1]
            + " & " + data[1].name.split(" ")[data[1].name.split(" ").length-1];
        }
      }
    );
    return this.selectedArtists;
  }

setCountriesArray(countries: any){
    this.countriesMap = new Map;
    if(countries!==null) {
      countries.forEach(
        (elem: string) => {
          if (!this.countriesMap.has(elem)) {
            this.countriesMap.set(elem, 1);
          } else {
            this.countriesMap.set(elem, this.countriesMap.get(elem)! + 1);
          }
        }
      );
    }
    return this.countriesMap;
  }

  setCitiesArray(cities: any){
    this.citiesMap = new Map;
    if(cities!=null){
      cities.forEach(
        (elem: string) =>
        {
          if(!this.citiesMap.has(elem)){
            this.citiesMap.set(elem,1);
          } else {
            this.citiesMap.set(elem, this.citiesMap.get(elem)! +1 );
          }
        }
      );
    }
    return this.citiesMap;
  }

}

