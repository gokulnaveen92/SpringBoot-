import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angularapp';

  tasks:string[] = [];

  addTask(task:string){
    this.tasks.push(task);
  }

  deleteTask(index:number){
    this.tasks.splice(index,1);
  }
}
