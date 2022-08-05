import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';

@Component({
  selector: 'app-nodes-coloring',
  templateUrl: './nodes-coloring.component.html',
  styleUrls: ['./nodes-coloring.component.scss']
})
export class NodesColoringComponent implements OnInit, OnChanges {

  constructor() { }

  @Input('inputNetwork') network: any;
  @Input('current') current: number[] = [];

  ngOnInit(): void {

  }

  ngOnChanges(changes: SimpleChanges): void {
  }

}
