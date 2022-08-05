import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-show-map',
  templateUrl: './show-map.component.html',
  styleUrls: ['./show-map.component.scss']
})
export class ShowMapComponent implements OnInit {

  @Input('current') current: number[] = [];
  @Input('selectedArtists') selectedArtists: string = "Nothing selected";
  @Input('selectedArtistsIds') selectedArtistsIds: number[] = [];

  constructor(private router: Router) { }

  ngOnInit(): void {

  }

  navigate(network: number[]){
    const newTab = this.router.serializeUrl(this.router.createUrlTree(['/map'], { queryParams: {artists: network} }));
    window.open(newTab, '_blank');
  }


}
