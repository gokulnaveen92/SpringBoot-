import { Component, OnInit } from '@angular/core';
import { Book } from '../book';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  books:Book[] = [];
  newBookTitle:string ="";
  constructor() { }

  ngOnInit(): void {
  }

  addBook(){
    if(this.newBookTitle.trim()){
      var book:Book={
        title:this.newBookTitle,
        completed:false
      }
      this.books.push(book);
    }
    this.newBookTitle="";
  }

  completeBook(book:Book){
    if(!book.completed){
      book.completed = true;
      document.getElementById("comButton").classList.add("line-through");
    }else{
      book.completed = false;
      document.getElementById("comButton").classList.remove("line-through");
    }
  }

  deleteBook(index:number){
    this.books.splice(index,1);
  }

}
