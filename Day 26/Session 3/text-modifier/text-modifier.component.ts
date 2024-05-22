import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-text-modifier',
  templateUrl: './text-modifier.component.html',
  styleUrls: ['./text-modifier.component.css']
})
export class TextModifierComponent implements OnInit {
  originalText:string;
  modifiedText:string;

  constructor() { 
    this.originalText="";
    this.modifiedText="";
  }

  ngOnInit(): void {
  }

  applyUppercase(){
    this.modifiedText = this.originalText.toUpperCase();
  }

  applyLowercase(){
    this.modifiedText = this.originalText.toLowerCase();
  }

  applyReverse(){
    this.modifiedText = this.originalText.split("").reverse().join("");
  }

  clearText(){
    this.originalText = "";
    this.modifiedText = "";
  }

}
