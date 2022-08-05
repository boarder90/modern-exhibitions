import {Component, Input, OnInit} from '@angular/core';
import {ExhibitionDto} from "../../../dtos/ExhibitionDto";

@Component({
  selector: 'app-exhibitions-list',
  templateUrl: './exhibitions-list.component.html',
  styleUrls: ['./exhibitions-list.component.scss']
})
export class ExhibitionsListComponent implements OnInit {

  constructor() { }

  @Input()
  exhibitions: ExhibitionDto[] = [];

  ngOnInit(): void {
  }

}
