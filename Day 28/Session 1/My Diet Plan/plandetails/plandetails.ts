import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { meals } from 'src/meals';

@Component({
  selector: 'app-plandetails',
  templateUrl: './plandetails.component.html',
  styleUrls: ['./plandetails.component.css']
})
export class PlandetailsComponent implements OnInit {

  mealId:number;
  meal:any;
  constructor(private at:ActivatedRoute ) { }

  ngOnInit(): void {
    this.mealId = parseInt(this.at.snapshot.paramMap.get("id"));
    this.getMealById(this.mealId);
  }

  getMealById(id:number){
    this.meal=meals.find(m=>m.id === id);
    //console.log(this.meal);
  }

}
