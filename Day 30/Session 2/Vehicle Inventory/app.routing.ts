import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VehicleListComponent } from './vehicle-list/vehicle-list.component';
import { AddVehicleComponent } from './add-vehicle/add-vehicle.component';

const routes: Routes = [
  {path:'vehicles',component:VehicleListComponent},
  {path:'addVehicle',component:AddVehicleComponent},
  {path:'',redirectTo:'vehicles',pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
