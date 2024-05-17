//write your code here
function shopping(){
    this.groceries = [];
    this.totalPrice=0;
    
    this.addItem=function(item,price){
        let products ={itemname:item,price:price};
        this.groceries.push(products);
    }

    this.calculateTotalPrice=function(){
        this.totalPrice =this.groceries.reduce((sum,product)=>sum+product.price,0)
        return this.totalPrice;
    }

}

let shoppingList = new shopping();
shoppingList.addItem("biscuits",100);
shoppingList.addItem("softdrinks",100);
console.log(shoppingList.calculateTotalPrice());
//console.log(shoppingList.groceries);
module.exports=shoppingList;
