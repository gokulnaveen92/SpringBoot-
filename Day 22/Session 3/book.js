class Book{
    constructor(title,author,price,quantity){
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
       
        this.getBookInfo = function(){
            return "Title: "+this.title+", Author: "+this.author+", Price: $"+this.price+", Quantity: "+this.quantity;
        }
    }
}
 
inventory = [];
 
function createBook(title,author,price,quantity){
    return new Book(title,author,price,quantity);
}
 
function addBookToInventory(title,author,price,quantity){
    inventory.push(createBook(title,author,price,quantity));
}
 
function displayInventory(){
    inventory.forEach(b => console.log(b.getBookInfo()));
}
 
function calculateTotalValue(){
    var total = 0;
    inventory.forEach(b => total += (b.quantity * b.price));
    return total;
}
 
module.exports = {Book, addBookToInventory, displayInventory, calculateTotalValue, createBook, inventory}
has context menu
