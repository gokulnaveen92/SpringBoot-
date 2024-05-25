import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  searchText:string="";
  items:any[]=[
    {
      id:1,
      name:"Apple",
      category:"Fruit"
    },
    {
      id:2,
      name:"Banana",
      category:"Fruit"
    },
    {
      id:3,
      name:"Carrot",
      category:"Vegetable"
    }
  ]
  constructor() { }

  ngOnInit(): void {
  }

}
