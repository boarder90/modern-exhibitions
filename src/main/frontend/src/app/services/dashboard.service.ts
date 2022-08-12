import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  countriesMap: Map<string, number> = new Map;
  citiesMap: Map<string, number> = new Map;
  selectedArtists: string = "Nothing selected";

  constructor() { }

  /**
   * Returns a map of countries, which contains the country as a key and the number of occurrences as a value
   *
    * @param countries
   */
setCountriesMap(countries: any){
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

  /**
   * Returns a map of cities, which contains the city as a key and the number of occurrences as a value
   *
   * @param cities
   */
  setCitiesMap(cities: any){
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

