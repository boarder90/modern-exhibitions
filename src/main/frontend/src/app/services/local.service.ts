import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalService {

  constructor() { }

  /**
   * Stores network in local storage.
   * @param key name of the network
   * @param value networkIds
   */
  public saveNetwork(key: string, value: number[]) {

    localStorage.setItem(key, JSON.stringify(value));
  }

  /**
   * Returns network from local storage
   * @param key name of the network
   */
  public getNetwork(key: string) {
    if(localStorage.getItem(key) !== null){
      // @ts-ignore
      return JSON.parse(localStorage.getItem(key));
    } else {
      return null;
    }
  }

  /**
   * Returns all networks from local storage as a map.
   */
  public getAllNetworksAsMap(){
    const map = new Map<string, number[]>();
    Object.keys(localStorage).forEach(function(key){
      map.set(key,JSON.parse(localStorage.getItem(key)!));
    });
    return map;
  }

  /**
   * Deletes network
   * @param key name of the network which should be deleted
   */
  public removeNetwork(key: string) {
    localStorage.removeItem(key);
  }

  /**
   * Clears local storage
   */
  public clear() {
    localStorage.clear();
  }
}
