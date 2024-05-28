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
    //console.log(this.passwordMismatch)
    if(form.value.password !== form.value.confirmPassword){
      this.passwordMismatch=true;
    }else{
      if(form.valid){
        this.isSubmitted = true;
        this.passwordMismatch = false;
        form.reset();
      }
    }

    

  }
}
