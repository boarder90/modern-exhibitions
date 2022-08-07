import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {Options} from "@angular-slider/ngx-slider";
import {CountryPipe} from "../../util/country-pipe";
import {ArtistDto} from "../../dtos/ArtistDto";
import {LocalService} from "../../services/local.service";
import {LocationService} from "../../services/HttpServices/location.service";
import {Router} from "@angular/router";
import {ArtistService} from "../../services/HttpServices/artist.service";
import {DashboardService} from "../../services/dashboard.service";

@Component({
  selector: 'app-network-overview',
  templateUrl: './network-overview.component.html',
  styleUrls: ['./network-overview.component.scss']
})
export class NetworkOverviewComponent implements OnInit {

  @ViewChild('matrixComponent') network: any;

  title = "ng-sigma-example";
  value: number = 1905;
  countriesMap: Map<string, number> = new Map;
  citiesMap: Map<string, number> = new Map;
  countryPipe: CountryPipe | undefined;
  artist!: ArtistDto;
  part!: boolean;
  cities: string[] = [];
  countries: string[] = [];
  current: number[] = [];
  year!: number;
  public centralities: boolean = true;
  reset!:boolean;
  currentColor:string = "default";
  private networkIds1: number[] = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30] ;
  private networkIds2: number[] = [1,2,3,4,5,6,7] ;
  private one: number[] = [1] ;
  private five: number[] = [1,2,3,4,5] ;
  private ten: number[] = [1,2,3,4,5,6,7,8,9,10,11,12,13] ;
  private fifteen: number[] = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20] ;
  private twenty: number[] = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26] ;
  private twentyfive: number[] = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,20,21,22,23,24,25,26,27,28,29,30,31,32,33] ;
  private thirty: number[] = [1,2,3,4,5,6,20,21,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51] ;
  private networkIds3: number[] = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15] ;
  map: Map<string, number[]> = new Map();
  bool: boolean = true;
  yearly: boolean = false;
  selected: boolean = false;
  selectedArtists: string = "Nothing selected";
  selectedArtistsIds: number[] = [];

  options: Options = {
    stepsArray: [
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
    ],
    showTicks: true,
    translate: (value: number): string => {
      switch (value) {
        case 1916:
          return "1915/1916";
        case 1905:
          return '1904/1905';
        case 1907:
          return "1906/1907";
        case 1908:
          return '1907/1908';
        case 1909:
          return "1908/1909";
        case 1910:
          return '1909/1910';
        case 1911:
          return "1910/1911";
        case 1912:
          return '1911/1912';
        case 1913:
          return "1912/1913";
        case 1914:
          return '1913/1914';
        case 1915:
          return "1914/1915";
        case 1906:
          return "1905/1906";
        default:
          return '';
      }
    }
  };


  constructor(private localService: LocalService, private locationService: LocationService,
              private cdr: ChangeDetectorRef, private router: Router, private artistService: ArtistService,
              private dashboardService: DashboardService) {
  }

  ngOnInit(): void {
    this.map = this.localService.getAllNetworksAsMap();
    console.log(this.networkIds1)
    this.map.set("Big network", this.networkIds1);
    this.map.set("Small network", this.networkIds2);
    this.map.set("Medium network", this.networkIds3);
    this.map.set("One", this.one);
    this.map.set("Five", this.five);
    this.map.set("Ten", this.ten);
    this.map.set("Fifteen", this.fifteen);
    this.map.set("Twenty", this.twenty);
    this.map.set("twentyfive", this.twentyfive);
    this.map.set("thirty", this.thirty);
    this.cdr.detectChanges();
  }

  getLocations(ids:number[]){
    this.locationService.getLocationsOfNetwork(ids).subscribe(
      data => {
        this.cities = data.cities;
        this.countries = data.countries;
        this.cdr.detectChanges();
      }
    )
  }

  setCurrent(networkIds: number[]){
    this.current = networkIds;
  }

  setArtist(e: any){
    this.artist = e;
    console.log(this.artist);
  }

  setCountriesArray(e: any){
    this.countriesMap = new Map;
    this.citiesMap = new Map;
    this.countriesMap = this.dashboardService.setCountriesArray(e);
  }

  setYear(e:any){
    this.year = e;
  }

   artistLinkSelected(e:string){
    const arr = e.split(",");
    this.selectedArtistsIds = arr.map(Number);
     this.artistService.getArtistsByIds(this.selectedArtistsIds).subscribe(
       data => {
         if(data.length>1){
           this.selectedArtists = data[0].name.split(" ")[data[0].name.split(" ").length-1]
             + " & " + data[1].name.split(" ")[data[1].name.split(" ").length-1];
           this.selected = true;
         }
       }
     );
  }

  setCitiesArray(e: any){
    this.citiesMap = new Map;
    this.countriesMap = new Map;
    this.citiesMap = this.dashboardService.setCitiesArray(e);
  }

}
