import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

  vehicles:any[]=[];

  constructor() { }

  getVehicles(){
    return this.vehicles;
  }

  addVehicle(vehicle:any){
    this.vehicles.push(vehicle);
  }

  deleteVehicle(index:number){
    this.vehicles.splice(index,1);
  }
}

