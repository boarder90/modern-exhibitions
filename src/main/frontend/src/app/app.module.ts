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
import { ArtistsOverviewComponent } from './components/network-creation/artists-search/artists-overview.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { NetworkOverviewComponent } from './components/matrix-dashboard/network-overview.component';
import { NetworkDetailComponent } from './components/network-creation/ego-network/network-detail.component';
import { MatrixComponent } from './components/matrix-dashboard/matrix/matrix.component';
import {CountryPipe} from "./util/country-pipe";
import {FaIconLibrary, FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {faCircle} from "@fortawesome/free-solid-svg-icons";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { NodeLinkComponent } from './components/network-creation/node-link.component';
import {MdbRadioModule} from "mdb-angular-ui-kit/radio";
import { ModalComponent } from './components/modals/modal/modal.component';
import { NodeLinkTestComponent } from './components/node-link-dashboard/node-link-test.component';
import { NetworkComponent } from './components/node-link-dashboard/network/network.component';
import {SexPipe} from "./util/sex-pipe";
import {NotifierModule} from "angular-notifier";
import { ViewModalComponent } from './components/modals/view-modal/view-modal.component';
import { ExhibitionsListComponent } from './components/map/exhibitions-list/exhibitions-list.component';
import {CityPipe} from "./util/city-pipe";
import { NodesColoringComponent } from './components/dashboard/nodes-coloring/nodes-coloring.component';
import { MenuComponent } from './components/network-creation/menu/menu.component';
import { CentralityComponent } from './components/dashboard/centrality/centrality.component';
import { FilterLinksComponent } from './components/dashboard/filter-links/filter-links.component';
import { SelectionComponent } from './components/dashboard/selection/selection.component';
import { ShowMapComponent } from './components/dashboard/show-map/show-map.component';
import { ToggleGranularityComponent } from './components/dashboard/toggle-granularity/toggle-granularity.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MapComponent,
    ArtistsOverviewComponent,
    NetworkOverviewComponent,
    NetworkDetailComponent,
    MatrixComponent,
    CountryPipe,
    SexPipe,
    CityPipe,
    NodeLinkComponent,
    ModalComponent,
    NodeLinkTestComponent,
    NetworkComponent,
    ViewModalComponent,
    ExhibitionsListComponent,
    NodesColoringComponent,
    MenuComponent,
    CentralityComponent,
    FilterLinksComponent,
    SelectionComponent,
    ShowMapComponent,
    ToggleGranularityComponent
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
        ReactiveFormsModule,
        FontAwesomeModule,
        BrowserModule,
        BrowserAnimationsModule,
        HttpClientModule,
        AppRoutingModule,
        MdbRadioModule,
        NotifierModule.withConfig(
            {
                position: {
                    horizontal: {
                        position: 'right',
                        distance: 12
                    },
                    vertical: {
                        position: 'top',
                        distance: 12,
                        gap: 10
                    }
                }
            }
        ),
        FormsModule
    ],
  providers: [CountryPipe, SexPipe, CityPipe],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(library: FaIconLibrary) {
    library.addIcons(faCircle);
  }
}
