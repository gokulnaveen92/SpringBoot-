import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-countdown',
  templateUrl: './countdown.component.html',
  styleUrls: ['./countdown.component.css']
})
export class CountdownComponent implements OnInit {

  countdown:number;
  intervalId:any;
  constructor() {
    this.countdown=0;
    this.intervalId=null;
   }

  ngOnInit(): void {
  }

  startCountdown(seconds:number){
    this.countdown=seconds;
    this.intervalId=setInterval(()=>{    
      this.countdown--;
      if(this.countdown==0){
        clearInterval(this.intervalId);
      }
    },1000);
  }

}
