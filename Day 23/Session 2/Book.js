class Book{
    constructor(title,author){
        this.title = title;
        this.author = author;
        this.isAvailable = true;

        this.checkoutBook=function(){
            this.isAvailable = false;
        }

        this.returnBook=function(){
            this.isAvailable = true;
        }
    }
}

let books = [
    new Book("book 1","author 1"),
    new Book("book 2","author 2"),
    new Book("book 3","author 3")
];


let Checkin = function(){
    for(let i=0;i<books.length;i++){
        console.log(books[i].title+" "+books[i].author+" "+books[i].isAvailable+".");
    }
    console.log();
}

Checkin ();
books[0].checkoutBook();
Checkin ()
books[0].returnBook();
Checkin ();

module.exports = Book;
