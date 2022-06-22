import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { MdbAccordionModule } from 'mdb-angular-ui-kit/accordion';
import { MdbCarouselModule } from 'mdb-angular-ui-kit/carousel';
import { MdbCheckboxModule } from 'mdb-angular-ui-kit/checkbox';
import { MdbCollapseModule } from 'mdb-angular-ui-kit/collapse';
import { MdbDropdownModule } from 'mdb-angular-ui-kit/dropdown';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { MdbModalModule } from 'mdb-angular-ui-kit/modal';
import { HttpClientModule } from '@angular/common/http';

import { MdbTabsModule } from 'mdb-angular-ui-kit/tabs';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { MapComponent } from './components/map/map.component';
import {NgxSliderModule} from "@angular-slider/ngx-slider";
import {LeafletMarkerClusterModule} from "@asymmetrik/ngx-leaflet-markercluster";
import { ArtistsOverviewComponent } from './components/artists-overview/artists-overview.component';
import {ReactiveFormsModule} from "@angular/forms";
import { NetworkOverviewComponent } from './components/network-overview/network-overview.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MapComponent,
    ArtistsOverviewComponent,
    NetworkOverviewComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        MdbCarouselModule,
        MdbAccordionModule,
        MdbCheckboxModule,
        MdbModalModule,
        MdbCollapseModule,
        MdbDropdownModule,
        MdbFormsModule,
        MdbTabsModule,
        LeafletModule,
        HttpClientModule,
        NgxSliderModule,
        LeafletMarkerClusterModule,
        ReactiveFormsModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
