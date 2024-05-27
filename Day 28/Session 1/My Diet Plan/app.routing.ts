import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DietplanComponent } from './dietplan/dietplan.component';
import { PlandetailsComponent } from './plandetails/plandetails.component';

const routes: Routes = [
  {path:'dietplan',component:DietplanComponent},
  {path:'plandetail/:id',component:PlandetailsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
