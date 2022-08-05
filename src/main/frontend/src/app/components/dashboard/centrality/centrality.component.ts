import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';

@Component({
  selector: 'app-centrality',
  templateUrl: './centrality.component.html',
  styleUrls: ['./centrality.component.scss']
})
export class CentralityComponent implements OnInit, OnChanges{

  @Input('inputNetwork') network: any;
  @Input('current') current: number[] = [];
  @Input('yearly') yearly: boolean = false;
  @Input('reset') resetCentralities: boolean = true;

  constructor() { }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges) {
    this.resetCentralities = true;
  }

}
