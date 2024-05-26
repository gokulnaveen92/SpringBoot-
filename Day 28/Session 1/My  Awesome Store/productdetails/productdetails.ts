import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-productdetails',
  templateUrl: './productdetails.component.html',
  styleUrls: ['./productdetails.component.css']
})
export class ProductdetailsComponent implements OnInit {

  id:string;
  description:string;
  price:string;

  constructor(private ru:Router,private actv:ActivatedRoute) { }

  ngOnInit(): void {
    this.id=this.actv.snapshot.paramMap.get("id");
    this.description=this.actv.snapshot.queryParamMap.get("description");
    this.price=this.actv.snapshot.queryParamMap.get("price");
  }

  closeModal(){
    this.ru.navigate(['/productlist']);
  }

}
