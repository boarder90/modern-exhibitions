import {Component, OnInit} from '@angular/core';
import { MdbModalRef } from 'mdb-angular-ui-kit/modal';
import {ArtistDto} from "../../../dtos/ArtistDto";
import {LocalService} from "../../../services/local.service";

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
})
export class ModalComponent implements OnInit{
  constructor(public modalRef: MdbModalRef<ModalComponent>, private localService: LocalService) {}

  name = "";
  artists!: ArtistDto[];
  number = [1,2,3,4];
  selection!: number[];

  ngOnInit(): void {
  }

  remove(artist: ArtistDto){
    const index = this.selection.indexOf(artist.id);
    const index2 = this.artists.indexOf(artist);
    if(index>-1) {
      this.selection.splice(index, 1);
      this.localService.saveNetwork(this.name,this.selection);
      this.artists.splice(index2, 1);
    }
  }

}
