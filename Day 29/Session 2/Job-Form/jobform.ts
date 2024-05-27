import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-jobform',
  templateUrl: './jobform.component.html',
  styleUrls: ['./jobform.component.css']
})
export class JobformComponent implements OnInit {

  submitted:boolean = false;
  formData:any;

  genders:string[]=["Male","Female","Others"];
  jobPositions:string[]=["Manager","SME","SDE","Trainee",];
  years:String[]=["2-4","5-10","10-15"];

  constructor() { }

  ngOnInit(): void {
  }

  onSubmit(form:NgForm){
    if(form.valid){
      this.submitted = true;
      this.formData=form;
      //form.reset();
    }

    //console.log(form);
    //console.log(this.formData);
  }

  closeModal(){
    this.submitted = false;
    //console.log("im closing");
    this.formData.reset();
  }

}
