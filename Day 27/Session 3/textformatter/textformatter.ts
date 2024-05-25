import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-text-formatter',
  templateUrl: './text-formatter.component.html',
  styleUrls: ['./text-formatter.component.css']
})
export class TextFormatterComponent implements OnInit {
  inputText:string = "";
  selectedFormat:string = "";

  constructor() { }

  ngOnInit(): void {
  }

  formatText(str:string){
    this.selectedFormat = str;
  }

}
