import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MapComponent} from "./components/map/map.component";
import {ArtistsOverviewComponent} from "./components/artists-overview/artists-overview.component";
import {NetworkOverviewComponent} from "./components/network-overview/network-overview.component";

const routes: Routes = [
  {path:'',pathMatch:'full',redirectTo:'map'},
  {path:'map', component: MapComponent},
  {path:'artists', component: ArtistsOverviewComponent},
  {path:'network', component: NetworkOverviewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
