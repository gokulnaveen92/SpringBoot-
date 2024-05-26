import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PRODUCTS } from 'src/products';

@Component({
  selector: 'app-productlist',
  templateUrl: './productlist.component.html',
  styleUrls: ['./productlist.component.css']
})
export class ProductlistComponent implements OnInit {

  products:any[]=PRODUCTS;

  constructor(private rt:Router) { }

  ngOnInit(): void {
  }

  goToDetails(product){
    this.rt.navigate(['/productdetails',product.id],{queryParams:{description: product.description,price:product.price}});
  }

}
