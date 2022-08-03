import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MapComponent} from "./components/map/map.component";
import {ArtistsOverviewComponent} from "./components/network-creation/artists-search/artists-overview.component";
import {NetworkOverviewComponent} from "./components/matrix-dashboard/network-overview.component";
import {MatrixComponent} from "./components/matrix-dashboard/matrix/matrix.component";
import {NodeLinkComponent} from "./components/network-creation/node-link.component";
import {NodeLinkTestComponent} from "./components/node-link-dashboard/node-link-test.component";

const routes: Routes = [
  {path:'',pathMatch:'full',redirectTo:'map'},
  {path:'map/:artists', component: MapComponent},
  { path: 'map', component: MapComponent },
  {path:'artists', component: ArtistsOverviewComponent},
  {path:'network', component: NetworkOverviewComponent},
  {path:'matrix', component: MatrixComponent},
  {path:'network-creation', component: NodeLinkComponent},
  {path:'node-link', component: NodeLinkTestComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
