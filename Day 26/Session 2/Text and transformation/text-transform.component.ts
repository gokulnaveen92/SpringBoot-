import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-text-transformation',
  templateUrl: './text-transformation.component.html',
  styleUrls: ['./text-transformation.component.css']
})
export class TextTransformationComponent implements OnInit {
  transformedText:string ="";
  textLength:number=0;
  lowercaseCount:number=0;
  uppercaseCount:number=0;
  numberCount:number=0;
  specialCharCount:number=0;
  arr:string[]=[];

  constructor() {
 
   }

  ngOnInit(): void {
  }

  transformText(inputText:string){
    this.textLength = 0;
    this.lowercaseCount=0;
    this.uppercaseCount =0;
    this.numberCount =0;
    this.specialCharCount =0;
    this.transformedText=inputText.toUpperCase();
    this.textLength = inputText.length;

    this.arr= inputText.split('');

    var uppArr:string[] = this.arr.filter(char=> /[A-Z]/.test(char));
    this.uppercaseCount=uppArr.length;

    var lwr:string[] = this.arr.filter(char=> /[a-z]/.test(char));
    this.lowercaseCount=lwr.length;


    var numArr:string[] = this.arr.filter(char=> /\d/.test(char));
    this.numberCount = numArr.length;

    var splArr:string[] = this.arr.filter(char => /[^\w]|_/.test(char));
    this.specialCharCount = splArr.length;

  }

}
