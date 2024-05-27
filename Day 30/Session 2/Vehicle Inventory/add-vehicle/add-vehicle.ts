import { Component, OnInit } from '@angular/core';
import { VehicleService } from '../services/vehicle.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-vehicle',
  templateUrl: './add-vehicle.component.html',
  styleUrls: ['./add-vehicle.component.css']
})
export class AddVehicleComponent implements OnInit {

  name:string ="";
  type:string ="";
  brand:string ="";

  constructor(private service:VehicleService ,private rt:Router) { }

  ngOnInit(): void {
  }

  addVehicle(){
    let v = {name:this.name,type:this.type,brand:this.brand}
    this.service.addVehicle(v);
    this.rt.navigate(['/vehicles']);
  }

}
