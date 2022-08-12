import { Injectable } from '@angular/core';
import {NetworkDto} from "../dtos/NetworkDto";
import {YearlyNetworkDto} from "../dtos/YearlyNetworkDto";

@Injectable({
  providedIn: 'root'
})

export class MatrixService {

  constructor() { }

  /**
   *  Copyright 2020 Harrison (Harry) Cramer. This code is part of the block "Adjacency Matrix" and licensed under the
   *  GNU General Public License, version 3. For the full license text, please see the file
   *  License.txt or visit https://opensource.org/licenses/GPL-3.0
   *
   *  2022 - Modified by Adam Nedas.
   *
   */
  getTotalMatrix(network: NetworkDto){
    let map = this.getMap(network.links)
    const matrix = [];
    for (let a in network.nodes) {
      for (let b in network.nodes) {
        const countries: string[] = [];
        const cities: string[] = [];
        let grid =
          {
            id: network.nodes[a].id + "," + network.nodes[b].id,
            x: Number(b), y: Number(a), weight: 0, country: countries, city: cities
          };
        if (map.has(grid.id)) {
          grid = this.fillMatrix(grid, map);
        }
        matrix.push(grid);
      }
    }
    return matrix;
  }

  /**
   *  Copyright 2020 Harrison (Harry) Cramer. This code is part of the block "Adjacency Matrix" and licensed under the
   *  GNU General Public License, version 3. For the full license text, please see the file
   *  License.txt or visit https://opensource.org/licenses/GPL-3.0
   *
   *  2022 - Modified by Adam Nedas.
   *
   */
  fillMatrix(grid: any, map: any){
    grid.weight = map.get(grid.id).numExhibitions;
    map.get(grid.id).countries.forEach(
      (c: string) => {
        grid.country.push(c);
      }
    );
    map.get(grid.id).cities.forEach(
      (c: string) => {
        grid.city.push(c);
      });
    return grid;
  }

  /**
   *  Copyright 2020 Harrison (Harry) Cramer. This code is part of the block "Adjacency Matrix" and licensed under the
   *  GNU General Public License, version 3. For the full license text, please see the file
   *  License.txt or visit https://opensource.org/licenses/GPL-3.0
   *
   *  2022 - Modified by Adam Nedas.
   *
   */
  getUpperMatrix(network: YearlyNetworkDto, year: number){
    let map = this.getMap(network.links)
    const matrix = [];
    for (let a in network.nodes) {
      for (let b in network.nodes) {
        const countries: string[] = [];
        const cities: string[] = [];
        if(Number(a)<=Number(b)){
          let grid =
            {
              id: network.nodes[a].id + "," + network.nodes[b].id,
              x: Number(b), y: Number(a), weight: 0, country: countries, city: cities, year: year
            };
          if (Number(a) <= Number(b) && map.has(grid.id)) {
            grid = this.fillMatrix(grid, map);
          }
          matrix.push(grid);
        }
      }
    }
    return matrix;
  }

  /**
   *  Copyright 2020 Harrison (Harry) Cramer. This code is part of the block "Adjacency Matrix" and licensed under the
   *  GNU General Public License, version 3. For the full license text, please see the file
   *  License.txt or visit https://opensource.org/licenses/GPL-3.0
   *
   *  2022 - Modified by Adam Nedas.
   *
   */
  getLowerMatrix(network: YearlyNetworkDto, year: number){
    let map = this.getMap(network.links)
    const matrix = [];
    for (let a in network.nodes) {
      for (let b in network.nodes) {
        const countries: string[] = [];
        const cities: string[] = [];
        if(Number(a)>=Number(b)){
          let grid =
            {
              id: network.nodes[a].id + "," + network.nodes[b].id,
              x: Number(b), y: Number(a), weight: 0, country: countries, city: cities, year: year
            };
          if (Number(a) >= Number(b) && map.has(grid.id)) {
            grid = this.fillMatrix(grid, map);
          }
          matrix.push(grid);
        }
      }
    }
    return matrix;
  }

  /**
   *  Copyright 2020 Harrison (Harry) Cramer. This code is part of the block "Adjacency Matrix" and licensed under the
   *  GNU General Public License, version 3. For the full license text, please see the file
   *  License.txt or visit https://opensource.org/licenses/GPL-3.0
   *
   *  2022 - Modified by Adam Nedas.
   *
   */
  getMap(links: any[]){
    let map = new Map();
    for (let x in links) {
      const id = links[x].source + "," + links[x].target;
      const id2 = links[x].target + "," + links[x].source;
      map.set(id, links[x]);
      map.set(id2, links[x]);
    }
    return map;
  }

  /**
   * Returns the size of a single rectangle depending on the row length
   *
   * @param length
   */
  calculateSizeOfRectTotal(length: number): number{
    if(length<=5){
      return 130;
    } else if (length>5 && length<=10){
      return 65;
    } else if (length>10 && length<=15){
      return 45;
    } else if (length>15 && length<=20){
      return 33;
    } else if (length>20 && length<=25) {
      return 27;
    } else {
      return 22;
    }
  }

  /**
   * Returns the size of a single yearly rectangle depending on the row length
   *
   * @param length
   */
  calculateSizeOfRectYearly(length: number): number{
    if(length<=5){
      return 50;
    } else if (length>5 && length<=10){
      return 45;
    } else if (length>10 && length<=15){
      return 31;
    } else if (length>15 && length<=20){
      return 23;
    } else if (length>20 && length<=25) {
      return 18;
    } else {
      return 15;
    }
  }
}
