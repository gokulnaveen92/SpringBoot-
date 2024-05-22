import { Component, OnInit } from '@angular/core';
import { PROMPTS } from 'src/prompts';

@Component({
  selector: 'app-promptdisplay',
  templateUrl: './promptdisplay.component.html',
  styleUrls: ['./promptdisplay.component.css']
})
export class PromptdisplayComponent implements OnInit {
  prompts:string[];
  currentPromptIndex:number;
  prompt:string;

  constructor() { 
    this.prompts=PROMPTS;
    this.currentPromptIndex=0;
    this.nextPrompt();
  }

  ngOnInit(): void {
  }

  nextPrompt(){
   // console.log(this.currentPromptIndex);

    this.prompt=this.prompts[this.currentPromptIndex];
    this.currentPromptIndex++;
    if(this.currentPromptIndex > this.prompts.length - 1){
      this.currentPromptIndex = 0;
    }
    
  }

}
