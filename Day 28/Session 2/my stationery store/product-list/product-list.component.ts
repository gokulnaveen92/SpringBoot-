import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products:any[]=[
    {
      id:1,
      name:"pencil",
      description:"A pencil is an object that you write or drwa with.It consisits of a thin piece of wood or draw with a rod of black or coloured substannce throught the middle",
      price:"10.99",
      imageUrl:"assets/pencil.jpg"
    },
    {
      id:2,
      name:"Scale",
      description:"A Scale is a set of levels or numbers which are used in particular system of measuring thing s ara used when comparing things",
      price:"19.99",
      imageUrl:"assets/scale.jpg"
    },
    {
      id:3,
      name:"Eraser",
      description:"An eraser ,piece of rubber or other material used to rub out marks made by ink,pencil,or chalk.The modern eraser is usually a mixture of an abrasive such as fine pumice, a rubbery matrix such as synthetic rubber or vinyl, and  other ingredients",
      price:"29.99",
      imageUrl:"assets/eraser.jpg"
    },
  ];

  @Output() productSelected:EventEmitter<any> = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  viewDetails(product){
    this.productSelected.emit(product);
  }
}
