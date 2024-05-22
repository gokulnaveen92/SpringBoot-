import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-displayclock',
  templateUrl: './displayclock.component.html',
  styleUrls: ['./displayclock.component.css']
})
export class DisplayclockComponent implements OnInit {

   currentDateTime:Date;
   currentDate:string;
   currentTime:string;

  constructor() { 
    this.currentDateTime=new Date();
    this.currentDate= this.currentDateTime.toDateString();
    this.currentTime = this.currentDateTime.toTimeString();
  }

  ngOnInit(): void {
    setInterval(()=>{
      this.currentDateTime=new Date();
      this.currentDate= this.currentDateTime.toDateString();
      this.currentTime = this.currentDateTime.toTimeString();
     },1000);  
  }

}
