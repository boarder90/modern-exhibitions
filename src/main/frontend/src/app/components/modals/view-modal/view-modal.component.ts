import { Component, OnInit } from '@angular/core';
import {ArtistDto} from "../../../dtos/ArtistDto";
import {MdbModalRef} from "mdb-angular-ui-kit/modal";
import {ModalComponent} from "../modal/modal.component";

@Component({
  selector: 'app-view-modal',
  templateUrl: './view-modal.component.html',
  styleUrls: ['./view-modal.component.scss']
})
export class ViewModalComponent implements OnInit {

  constructor(public modalRef: MdbModalRef<ModalComponent>) { }

  artists!: ArtistDto[];

  ngOnInit(): void {
  }

}
