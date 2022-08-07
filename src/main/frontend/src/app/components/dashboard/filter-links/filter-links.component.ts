import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';

@Component({
  selector: 'app-filter-links',
  templateUrl: './filter-links.component.html',
  styleUrls: ['./filter-links.component.scss']
})
export class FilterLinksComponent implements OnInit, OnChanges {

  @Input('inputNetwork') network: any;
  @Input('current') current: number[] = [];
  @Input('cities') cities: string[] = [];
  @Input('countries')   countries: string[] = [];
  @Input('year')  year: number = 1904;
  @Input('lower')  lower!: boolean;
  reset: boolean = true;
  @Input('reset') res!: boolean;
  @Output('currentColor') currentColor: EventEmitter<string>  = new EventEmitter<string>();
  constructor() { }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges) {
    if(changes['res']){
      this.reset = true;
      this.currentColor.emit("default")
    }
    if(changes['current']){
      this.reset = true;
      this.currentColor.emit("default")
    }
  }
}
