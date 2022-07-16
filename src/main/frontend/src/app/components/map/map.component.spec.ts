import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapComponent } from './map.component';
import {HttpClient, HttpHandler} from "@angular/common/http";
import {ActivatedRoute, convertToParamMap, RouterModule} from "@angular/router";
import {MdbCollapseModule} from "mdb-angular-ui-kit/collapse";
import {Observable, of} from 'rxjs';
import {QueryParam} from "../../util/QueryParam";
import {ActivatedRouteStub} from "../../util/stub";

describe('MapComponent', () => {
  let component: MapComponent;
  let fixture: ComponentFixture<MapComponent>;

  beforeEach(async () => {
    let activatedRouteSpy = {
      snapshot: {
        paramMap: convertToParamMap({
          artists: 134,
        })
      }
    };

    let activatedRoute = new ActivatedRouteStub();
    activatedRoute.setParamMap({artists: ''});
    await TestBed.configureTestingModule({
      imports: [MdbCollapseModule],
      declarations: [ MapComponent ],
      providers: [
        HttpClient,
        HttpHandler,
        RouterModule,
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get(): number[] {
                  return [2,4];
                },
              },
            },
          },
        },
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });



  it('should create', () => {

    expect(component).toBeTruthy();
  });

});

