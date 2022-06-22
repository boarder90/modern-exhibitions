import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import * as L from 'leaflet';
import {GeoJSON, latLng, tileLayer} from 'leaflet';
import {ExhibitionService} from "../../services/exhibition.service";
import {LocationDto} from "../../dtos/LocationDto";
import {Options} from "@angular-slider/ngx-slider";
import 'leaflet.markercluster';
import {Feature} from "geojson";
import {ExhibitionDto} from "../../dtos/ExhibitionDto";
import {Observable, ReplaySubject} from "rxjs";


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {

  streetMaps = tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    detectRetina: true,
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
  });

  show: boolean = false;
  year: number | null = 0;
  loaded: boolean = false;
  exhibitionsLoaded: boolean = false;
  exhibitions: ExhibitionDto[] = [];
  locations: LocationDto[] = [];
  exhibitions$: Observable<ExhibitionDto[]> = new ReplaySubject<ExhibitionDto[]>(1);
  layers: GeoJSON[] = [];
  totalExhibitions: GeoJSON[] = [];
  features: GeoJSON.Feature[] = [];
  yearlyExhibitions = new Map<number, GeoJSON[]>();
  numExhibitionsYear = new Map<number, number>();

  value: number = 1904;
  sliderOptions: Options = {
    showTicksValues: true,
    stepsArray: [
      { value: 1904},
      { value: 1905},
      { value: 1906},
      { value: 1907},
      { value: 1908},
      { value: 1909},
      { value: 1910},
      { value: 1911},
      { value: 1912},
      { value: 1913},
      { value: 1914},
      { value: 1915},
      { value: 1916}
    ]
  };

  constructor(private exhibitionService: ExhibitionService, private cdr:ChangeDetectorRef) {
  }

  ngOnInit(): void {
    this.loadExhibitions();
    this.loadExhibitionsYearly();
  }

  markerClusterGroup!: L.MarkerClusterGroup;
  data: GeoJSON[] = [];
  markerClusterOptions: L.MarkerClusterGroupOptions = {};


  options = {
    layers: [this.streetMaps],
    zoom: 4,
    center: latLng([48.155055, 11.401752])
  };

  onChange(e: any) {
    if(this.show) {
      this.value = e;
      this.data = this.yearlyExhibitions.get(this.value)!;
    }
  }

  onClick(e: any) {
    this.show=false;
   this.data = this.totalExhibitions;
  }

  showExhibitions(year: number | null, coordinates: number[] | null, artistIds: number[] | null){
    this.exhibitionService.getExhibitionsFiltered(coordinates,year,artistIds).subscribe(
      e=> {
        this.exhibitions = e;
        this.year = year;
      this.cdr.detectChanges();
      });
  }

  test(){
    this.exhibitions$.subscribe(
      e => {
        this.exhibitions = e;
        console.log(e);
      }
    )
  }

  private loadExhibitions() {
    this.exhibitionService.getExhibitionLocations().subscribe(
      data => {
        this.features = data.features;
        const size = this.biggestNumberInArray(this.features)
        this.features.forEach(
          f => {
            const curr = f.properties!['numExhibitions'];
            const circleSize = 58 * curr/size > 18 ?  58*curr/size : 18
            console.log(circleSize)
            this.totalExhibitions.push(
                L.geoJSON(f, {
                pointToLayer: function (feature, latlng) {
                  return L.marker(latlng, {
                  icon: L.divIcon({
                                        className: 'my-div-icon',
                                        html: '<button type="button" style="height: ' + circleSize + 'px; width:' + circleSize + 'px; opacity: 0.8;" class="btn btn-primary btn-floating">' +
                                          f.properties!['numExhibitions'] + '</button>'
                                      })
                  })
                }
              }).on('click', this.showExhibitions.bind(this, null, f.geometry.type ==="Point"? f.geometry.coordinates : null, null)));
          }
        )
        this.data = this.totalExhibitions;
        this.loaded=true;
        console.log(this.data)
      }
    )
  }

   biggestNumberInArray(arr: Feature[]) {
     let maxNumber = -Infinity;
     arr.forEach(f => { maxNumber =  f.properties!['numExhibitions'] > maxNumber ?  f.properties!['numExhibitions'] :  maxNumber; })
     return maxNumber;
   }

  private loadExhibitionsYearly() {

    this.exhibitionService.getExhibitionLocationsYearly().subscribe(
      data => {
          this.features = data.features;

        this.features.forEach(
          f => {
            const key = f.properties!['year'];
            if(this.numExhibitionsYear.has(key)){
              const currMaxSizeYear = this.numExhibitionsYear.get(key)
              if(f.properties!['numExhibitions']>currMaxSizeYear!){
                this.numExhibitionsYear.set(key,f.properties!['numExhibitions'])
              }
            } else {
              this.numExhibitionsYear.set(key, f.properties!['numExhibitions'])
            }
          });

        this.features.forEach(
            f => {
              const curr = f.properties!['numExhibitions'];
              const key = f.properties!['year'];
              const circleSize = 58 * curr/this.numExhibitionsYear.get(key)! > 18 ?  58*curr/this.numExhibitionsYear.get(key)! : 18
              const arr = this.yearlyExhibitions.get(key);
              if (this.yearlyExhibitions.has(key)) {
                  arr!.push(
                    L.geoJSON(f, {
                      pointToLayer: function (feature, latlng) {
                        return L.marker(latlng, {
                          icon: L.divIcon({
                            className: 'my-div-icon',
                            html: '<button type="button" style="height: ' + circleSize + 'px; width:' + circleSize + 'px; opacity: 0.8;" class="btn btn-primary btn-floating">' +
                              f.properties!['numExhibitions'] + '</button>'
                          })
                        })
                      }
                    }).on('click', this.showExhibitions.bind(this, key, f.geometry.type ==="Point"? f.geometry.coordinates : null, null)))
                this.yearlyExhibitions.set(key, arr!);
              } else {
                const newArr: GeoJSON[] = [];
                newArr.push(
                  L.geoJSON(f, {
                    pointToLayer: function (feature, latlng) {
                      return L.marker(latlng, {
                        icon: L.divIcon({
                          className: 'my-div-icon',
                          html: '<button type="button" style="height: ' + circleSize + 'px; width:' + circleSize + 'px; opacity: 0.8;" class="btn btn-primary btn-floating">' +
                            f.properties!['numExhibitions'] + '</button>'
                        })
                      })
                    }
                  }).on('click', this.showExhibitions.bind(this, key, f.geometry.type ==="Point"? f.geometry.coordinates : null, null)))
                this.yearlyExhibitions.set(key, newArr);
              }
              }
          )
      }
    );
}
}
