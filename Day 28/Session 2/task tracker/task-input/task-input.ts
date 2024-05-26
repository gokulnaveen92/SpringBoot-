import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-task-input',
  templateUrl: './task-input.component.html',
  styleUrls: ['./task-input.component.css']
})
export class TaskInputComponent implements OnInit {

  taskInput:string;
  @Output() taskAdded:EventEmitter<string>= new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  addTask(){
    if(this.taskInput){
      this.taskAdded.emit(this.taskInput);
      this.taskInput="";
    }

}
}
