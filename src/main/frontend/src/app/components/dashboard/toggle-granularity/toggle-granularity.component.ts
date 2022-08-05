import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-toggle-granularity',
  templateUrl: './toggle-granularity.component.html',
  styleUrls: ['./toggle-granularity.component.scss']
})
export class ToggleGranularityComponent implements OnInit {

  @Input('inputNetwork') network: any;

  constructor() { }

  ngOnInit(): void {
  }

}
