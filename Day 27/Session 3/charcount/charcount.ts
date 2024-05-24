import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-character-count',
  templateUrl: './character-count.component.html',
  styleUrls: ['./character-count.component.css']
})
export class CharacterCountComponent implements OnInit {
  inputText:string="";

  constructor() { }

  ngOnInit(): void {
  }

}
