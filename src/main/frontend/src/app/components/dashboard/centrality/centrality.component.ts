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
  @Input('filterActive') filterActive: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges) {
    if(this.current.length>0){
      this.network.resizeDefault(this.current);
    }
    this.resetCentralities = true;
  }

}
