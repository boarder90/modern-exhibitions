import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl} from "@angular/forms";
import {LocalService} from "../../../services/local.service";
import {NotifierService} from "angular-notifier";
import {ViewModalComponent} from "../../modals/view-modal/view-modal.component";
import {ArtistService} from "../../../services/HttpServices/artist.service";
import {MdbModalRef, MdbModalService} from "mdb-angular-ui-kit/modal";
import {ArtistDto} from "../../../dtos/ArtistDto";
import {ModalComponent} from "../../modals/modal/modal.component";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  @Input("nameSelected") nameSelected: boolean = false;
  nameControl = new FormControl('');
  name: string = "";
  selection: number[] = [];
  networks!: Map<string,number[]>;
  selectionArtists: ArtistDto[] = [];
  modalRef: MdbModalRef<ModalComponent> | null = null;
  @Output()  nameSelectedEmit: EventEmitter<any> = new EventEmitter();
  @Output()  nameOutputEmit: EventEmitter<any> = new EventEmitter();
  @Output()  selectionEmit: EventEmitter<any> = new EventEmitter();
  @Output()  selectedArtistsEmit: EventEmitter<any> = new EventEmitter();

  constructor(private localService: LocalService, private artistService: ArtistService,
              private modalService: MdbModalService, private notifierService: NotifierService, private cdr:ChangeDetectorRef) { }

  ngOnInit(): void {
    this.networks = this.localService.getAllNetworksAsMap();
  }

  setName(){
    this.name = this.nameControl.value;
    if(this.localService.getNetwork(this.name)){
      this.notifierService.notify('error', 'Network with name "' + this.name + '" already exists!');
    } else {
      this.localService.saveNetwork(this.name, this.selection);
      this.nameSelectedEmit.emit(true);
      this.nameOutputEmit.emit(this.name)
      this.nameSelected = true;
    }
  }

  remove(network: string){
    this.localService.removeNetwork(network);
    this.networks.delete(network);
    this.cdr.detectChanges();
  }

  view(network:string){
    const selection = this.localService.getNetwork(network);
    if(selection!==null && selection.length>=1) {
      this.artistService.getArtistsByIds(this.localService.getNetwork(network)).subscribe(
        data => {
          this.modalRef = this.modalService.open(ViewModalComponent, {
            modalClass: 'modal-dialog-centered',
            data: {artists: data},
          });
        });
    } else {
      this.modalRef = this.modalService.open(ViewModalComponent, {
        modalClass: 'modal-dialog-centered',
        data: {artists: []},
      });
    }
  }

  edit(network: string){
    this.artistService.getArtistsByIds(this.localService.getNetwork(network)).subscribe(
      data => {
        this.nameSelected = true;
        this.nameSelectedEmit.emit(true);
        this.name = network;
        this.nameOutputEmit.emit(this.name);
        this.selection = this.localService.getNetwork(network);
        this.selectionArtists = data;
        this.selectionEmit.emit(this.selection);
        this.selectedArtistsEmit.emit(this.selectionArtists);
      });
  }

}
