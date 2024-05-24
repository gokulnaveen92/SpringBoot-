import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-product-catalog',
  templateUrl: './product-catalog.component.html',
  styleUrls: ['./product-catalog.component.css']
})
export class ProductCatalogComponent implements OnInit {
  products:any[]=[
    {
      name:"Product 1",
      description:"Description of Product 1",
      price:10,
      category:"Category A"
    },    {
      name:"Product 2",
      description:"Description of Product 2",
      price:20,
      category:"Category B"
    },    {
      name:"Product 3",
      description:"Description of Product 3",
      price:30,
      category:"Category C"
    }
  ];

  categories:string[]=["All","Category A","Category B","Category C"];

  selectedCategory:string = "All";
  searchTerm:string="";
  cart:any[]=[];

  constructor() { }

  ngOnInit(): void {
  }

  addToCart(product:any){
    this.cart.push(product);
  }

  removeFromCart(item:any){
    this.cart.splice(this.cart.indexOf(item),1);
  }
}
