import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-profile-card',
  templateUrl: './user-profile-card.component.html',
  styleUrls: ['./user-profile-card.component.css']
})
export class UserProfileCardComponent implements OnInit {
  initialUserAge:number;
  userName:string;
  userAge:number;
  
  constructor() {
    this.initialUserAge=25;
    this.userName="John Doe";
    this.userAge = this.initialUserAge;
   }

  ngOnInit(): void {
  }

  incrementAge(){
    this.userAge++;
  }

  resetAge(){
    this.userAge=this.initialUserAge;
  }

  updateUserName(input:string){
    this.userName=input;
  }

}
