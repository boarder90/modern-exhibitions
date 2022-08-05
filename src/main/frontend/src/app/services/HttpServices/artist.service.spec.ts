import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TestBed } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import {ArtistService} from "./artist.service";

describe('HttpClient testing', () => {
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;
  let service: ArtistService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule ]
    });

    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(ArtistService);
  });

  it('can test HttpClient.get', () => {

    service.getArtistsById(2)
      .subscribe(() => {}
      );

    const req = httpTestingController.expectOne('http://localhost:8080/artists/2');

    expect(req.request.method).toEqual('GET');

    req.flush("ddd");

    httpTestingController.verify();
  });
});
