import {Component, OnInit, ViewChild} from '@angular/core';
import * as d3 from 'd3';
import {NetworkService} from "../../services/HttpServices/network.service";
import {ArtistService} from "../../services/HttpServices/artist.service";
import {ArtistDto} from "../../dtos/ArtistDto";
import {MdbModalRef, MdbModalService} from "mdb-angular-ui-kit/modal";
import {ModalComponent} from "../modals/modal/modal.component";
import {LocalService} from "../../services/local.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-node-link',
  templateUrl: './node-link.component.html',
  styleUrls: ['./node-link.component.scss']
})
export class NodeLinkComponent implements OnInit {

  constructor(private networkService: NetworkService, private artistService: ArtistService,
              private modalService: MdbModalService, private localService: LocalService, private  router: Router) { }

  @ViewChild('egoComponent') ego: any;

  modalRef: MdbModalRef<ModalComponent> | null = null;

  artistDto!: ArtistDto;
  egoDto!: ArtistDto;
  show: boolean = false;
  selected = undefined;
  rendered = false;
  loading = false;
  numArtists: number = 0;
  networks!: Map<string,number[]>;
  nameSelected = false;
  selection: number[] = [];
  selectionArtists: ArtistDto[] = [];
  name: string = "";


  ngOnInit(): void {
  }

  networkRendered(e: any){
    this.rendered = e;
  }

  getEgo(e: any){
    this.loading = true;
    this.egoDto = e;
    this.rendered = false;
    this.ego.getArtistById(e.id);
  }

  currentEgo(e: any){
    this.egoDto = e;
  }

  addToArr(artist: ArtistDto){
    if(this.selectionArtists.indexOf(artist)===-1 && this.selectionArtists.length<=30){
    this.selectionArtists.push(artist);
    this.selection.push(artist.id)
    this.localService.saveNetwork(this.name,this.selection);
    }
  }

  clickedArtist(e: any){
    this.artistDto = e;
  }

  showArtist(d: any, i:any){

    if(!this.selected){
      this.selected = i.id;
      d3.selectAll("circle").filter(function() {return d3.select(this).attr("id") == i.id})
        .attr('stroke', 'yellow').attr('stroke-width',7);
    } else {
      const value = this.selected;
      d3.selectAll("circle").filter(function() {
        return d3.select(this).attr("id") == value})
        .attr('stroke', 'white').attr('stroke-width',1.5);
      this.selected = i.id;
      d3.selectAll("circle").filter(function() { // @ts-ignore
        return d3.select(this).attr("id") == i.id})
        .attr('stroke', 'yellow').attr('stroke-width',7);
    }

    this.artistService.getArtistsById(i.id).subscribe(
      artist => {
        this.artistDto = artist;
        console.log(artist)
      }
    )
  }

  showNetwork(d:any){
    d3.select('#tooltip').style("opacity", .9)
      .style("left", `${d.pageX + 14}px`)
      .style("top", `${d.pageY + 14}px`)
  }

  reload(){
    this.router.navigateByUrl('/' , { skipLocationChange: true }).then(() => {
      this.router.navigate(['network-creation']);
    });
  }


  openModal() {
    this.modalRef = this.modalService.open(ModalComponent, {
      modalClass: 'modal-dialog-centered',
      data: { name: this.name, artists: this.selectionArtists, selection: this.selection},
    });
  }

}
