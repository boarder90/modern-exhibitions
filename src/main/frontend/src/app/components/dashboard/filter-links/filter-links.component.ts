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
  @Input('reset') reset: boolean = true;
  @Output('currentColor') currentColor: EventEmitter<string>  = new EventEmitter<string>();
  constructor() { }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges) {
    this.reset = true;
  }

}
