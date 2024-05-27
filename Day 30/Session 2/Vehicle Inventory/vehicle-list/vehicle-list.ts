import { Component, OnInit } from '@angular/core';
import { VehicleService } from '../services/vehicle.service';

@Component({
  selector: 'app-vehicle-list',
  templateUrl: './vehicle-list.component.html',
  styleUrls: ['./vehicle-list.component.css']
})
export class VehicleListComponent implements OnInit {

  vehicles:any[]=[];

  constructor(private service:VehicleService) { }

  ngOnInit(): void {
    this.vehicles=this.service.getVehicles();
  }

  deleteVehicle(index:number){
    this.service.deleteVehicle(index);
    this.vehicles=this.service.getVehicles();
  }

}
