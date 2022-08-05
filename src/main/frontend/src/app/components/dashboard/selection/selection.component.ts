import {Component, Input, OnInit} from '@angular/core';
import {ArtistDto} from "../../../dtos/ArtistDto";

@Component({
  selector: 'app-selection',
  templateUrl: './selection.component.html',
  styleUrls: ['./selection.component.scss']
})
export class SelectionComponent implements OnInit {

  @Input('countriesMap') countriesMap: Map<string, number> = new Map;
  @Input('citiesMap') citiesMap: Map<string, number> = new Map;
  @Input('yearly') yearly: boolean = false;
  @Input('artist') artist!: ArtistDto;
  @Input('year') year: number = 0;

  constructor() { }

  ngOnInit(): void {

  }

}
