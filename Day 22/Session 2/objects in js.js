function calculator(){

    //this.num1 = num1;
    //this.num2 = num2;

    this.add = function(num1,num2){
        return num1 + num2;
    }

    this.subtract = function(num1,num2){
        return num1 - num2;
    }

    this.multiply = function(num1,num2){
        return num1 * num2;
    }

    this.divide = function(num1,num2){
        if(num2==0){
            return "Cannot divide by zero";
        }else{
            return num1/num2;
        }
    }
}

let cal = new calculator();
console.log(cal.add(10,5));
console.log(cal.subtract(10,5));
console.log(cal.multiply(10,5));
console.log(cal.divide(10,5));
module.exports=cal;
