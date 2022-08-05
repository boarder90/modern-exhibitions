import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormControl} from "@angular/forms";
import {finalize, switchMap} from "rxjs";
import { debounceTime, tap } from 'rxjs/operators';
import {ArtistDto} from "../../../dtos/ArtistDto";
import {ArtistService} from "../../../services/HttpServices/artist.service";

@Component({
  selector: 'app-artists-overview',
  templateUrl: './artists-overview.component.html',
  styleUrls: ['./artists-overview.component.scss']
})
export class ArtistsOverviewComponent implements OnInit {

  artistName = new FormControl();
  artists: ArtistDto[] = [];
  movieActor = new FormControl();
  importMovieTitle = new FormControl();
  actorsLoading = false;
  artistsLoading = false;
  importMoviesLoading = false;
  showMenu = false;
  moviesByGenLoading = false;

  @Output() artistData: EventEmitter<any> = new EventEmitter();

  constructor(private artistService: ArtistService) { }

  ngOnInit(): void {
    this.getArtistByName()
  }

  getArtistByName(){
    this.artistName.valueChanges
      .pipe(
        debounceTime(500),
        tap(() => {
          this.artists = [];
          this.artistsLoading = true;
        }),
        switchMap(value => typeof value === 'string' && value.length>0 ? this.artistService.getArtistsByName(value)
          .pipe(
            finalize(() => {
              this.artistsLoading = false
            }),
          ) : this.empty()
        )
      )
      .subscribe(data => {
        this.artists = data;
      });
  }

  pushToDetailNetwork(e: any){
    this.artistName.reset();
    this.artists = [];
    this.artistData.emit(e);
  }

  empty(): never[] {
    this.artistsLoading = false;
    return this.artists = [];
  }
}
