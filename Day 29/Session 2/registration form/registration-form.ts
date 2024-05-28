import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-registration-form',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.css']
})
export class RegistrationFormComponent implements OnInit {

  isSubmitted:boolean=false;
  passwordMismatch:boolean= false;

  constructor() { }

  ngOnInit(): void {
  }


  onSubmit(form:NgForm){
    //console.log(form);
    console.log(this.passwordMismatch)
    if(!form.value.passwordMismatch===form.value.conformMismatch){
      this.passwordMismatch=true;
      console.log(this.passwordMismatch)
      return;
    }else{
      if(form.valid){
        this.passwordMismatch=false;
        //console.log(this.passwordMismatch)
          this.isSubmitted=true;
          //form.reset();
        }
      }
  }
}
