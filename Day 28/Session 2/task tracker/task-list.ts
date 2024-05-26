import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {

  @Input() tasks:string[];
  @Output() taskDeleted:EventEmitter<number> = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  deleteTask(index:number){
    this.taskDeleted.emit(index);
  }
}
