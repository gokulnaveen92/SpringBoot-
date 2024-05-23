import { Component, OnInit } from '@angular/core';
import { Item } from '../item';

@Component({
  selector: 'app-shopping-list',
  templateUrl: './shopping-list.component.html',
  styleUrls: ['./shopping-list.component.css']
})
export class ShoppingListComponent implements OnInit {

  items:Item[]=[];
  newItemName="";

  constructor() { }

  ngOnInit(): void {
  }

  addItem(){
    if(this.newItemName){
      var item:Item = {
        name:this.newItemName,
        purchased:false
      }

      this.items.push(item);
    }

    this.newItemName="";
  }

  purchasedItem(item:Item){
    item.purchased = ! item.purchased;
  }

  deleteItem(index:number){
    this.items.splice(index,1);
  }

}
