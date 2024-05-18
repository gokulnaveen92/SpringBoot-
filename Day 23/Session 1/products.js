//write your code here
class Product{
    constructor(name,price,quantityInStock){
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;

        this.buy = function(quantity){
            if(quantity<this.quantityInStock){
                this.quantityInStock-=quantity;
            }else{
                return "insufficient stock";
            }
        }

        this.restock=function(quantity){
            this.quantityInStock += quantity;
        }
    }

}

// let pro = new Product("Toys",1000,500);
// console.log(pro.quantityInStock)
// console.log(pro.buy(700));
// console.log(pro.buy(400));
// console.log(pro.quantityInStock)
// console.log(pro.restock(900));
// console.log(pro.quantityInStock)


module.exports=Product;
