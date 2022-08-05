import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalService {

  constructor() { }

  public saveNetwork(key: string, value: number[]) {

    localStorage.setItem(key, JSON.stringify(value));
  }

  public getNetwork(key: string) {
    if(localStorage.getItem(key) !== null){
      // @ts-ignore
      return JSON.parse(localStorage.getItem(key));
    } else {
      return null;
    }
  }

  public getAllNetworksAsMap(){
    const map = new Map<string, number[]>();
    Object.keys(localStorage).forEach(function(key){
      map.set(key,JSON.parse(localStorage.getItem(key)!));
    });
    return map;
  }
  public removeNetwork(key: string) {
    localStorage.removeItem(key);
  }

  public clear() {
    localStorage.clear();
  }
}
